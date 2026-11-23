/*
 * Copyright (c) 2026 Clay Lambert <the.sovereign.systems.architect@gmail.com>
 * All rights reserved.
 *
 * Author: Clay Lambert
 *
 * Licensed under the MIT License.
 * If you use or modify this source, you must include this copyright notice
 * and the MIT License permission notice in all copies or substantial portions
 * of the software, including modifications.
 */

package org.roux;

import java.io.IOException;
import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.zip.CRC32C;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import org.roux.api.MemoryInspector;
import org.roux.api.MemoryLayoutManager;
import org.roux.api.MemoryLifecycleManager;
import org.roux.api.MemoryStateController;
import org.roux.crypto.CryptoSink;
import org.roux.frame.FrameHeader;
import org.roux.id.RouxId;
import org.roux.id.RouxIdGenerator;
import org.roux.index.MonotonicIndex;
import org.roux.layout.RouxLayout;
import org.roux.lock.PinTable;
import org.roux.model.AllocationResult;
import org.roux.model.AllocationStatus;
import org.roux.model.DecodedFrame;
import org.roux.model.FrameType;
import org.roux.model.HealthReport;
import org.roux.model.IndexEntry;
import org.roux.model.LockToken;
import org.roux.model.MemoryAddress;
import org.roux.model.MemoryTelemetry;
import org.roux.model.OperationResult;
import org.roux.model.OperationStatus;
import org.roux.model.PageFlags;
import org.roux.model.RegionDescriptor;
import org.roux.model.Role;
import org.roux.model.SnapshotHandle;
import org.roux.model.SnapshotOptions;
import org.roux.model.MessageInspect;
import org.roux.page.Page;
import org.roux.page.PageHeader;
import org.roux.session.ClipRecord;
import org.roux.session.OpenResult;
import org.roux.session.RegionManifest;
import org.roux.session.RegionOwner;
import org.roux.session.HeadFile;
import org.roux.spill.RegionPack;
import org.roux.token.HeuristicTokenCounter;
import org.roux.tel.RouxCounters;
import org.roux.toon.ToonCodec;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md FINESSE:f-170
// @aa-sdd PK-1263 docs/specs/java/org/roux/roux.spec.md FINESSE:f-222
/**
 * Roux is the paged memory beside the terminal.
 * The RouxEngine type is that memory.
 * One region is stored for each conversation id.
 * The type implements {@link MemoryInspector}, {@link MemoryLayoutManager}, {@link MemoryStateController}, and {@link MemoryLifecycleManager}.
 * A region holds the hot page, the off-heap index, and the map from a {@link RouxId} to a message id.
 * The hot page receives writes.
 * A disk region publishes through {@link RegionPack}.
 * A memory-only region stays mapped and does not take a pack lock.
 * A named pack sits beside the username directory, and the region id stays the username.
 * An edit of an existing message appends a later frame with the same message id, and the scan moves that row when the later frame is MSG, while a continuation of that id leaves the row.
 *
 * A caller opens a region, appends a message, reads a window, and closes the region.
 * The type refuses an empty conversation id.
 * The type refuses a snapshot, a heap dump, and defragment in this version.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-design.md
 *
 * @author clayl (2026-08-25)
 */
@Component
// @aa-sdd PK-642 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-688 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-507 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-509 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-803 docs/specs/common/roux.spec.md
// @aa-sdd PK-806 docs/specs/common/roux.spec.md
// @aa-sdd PK-813 docs/specs/common/roux.spec.md
// @aa-sdd PK-815 docs/specs/common/roux.spec.md
// @aa-sdd PK-824 docs/specs/common/roux.spec.md
// @aa-sdd PK-517 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-1383 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-812 docs/specs/common/roux.spec.md
// @aa-sdd PK-522 docs/specs/java/org/roux/roux.spec.md
public class RouxEngine
        implements MemoryInspector, MemoryLayoutManager, MemoryStateController, MemoryLifecycleManager {

    private final RouxProperties properties;
    private final RegionPack regionPack;
    private final RouxCounters counters;
    private final CryptoSink cryptoSink;
    private final RouxIdGenerator idGenerator;
    private final ReentrantLock mapLock;
    private final Map<String, Region> regions;
    private final Set<String> ramRegions;
    private DeadPagePrune prune;

    /**
     * The constructor stores the collaborators and creates the region map.
     * The map lock guards the region map and the memory-only id set.
     * The region map keeps insertion order.
     *
     * @param properties The properties are the {@code roux} settings.
     * @param regionPack The pack writes {@code pages.bin}.
     * @param counters The counters hold the telemetry totals.
     * @param cryptoSink The sink refuses an enabled cipher.
     * @param idGenerator The generator mints a {@link RouxId} for a new message.
     */
    public RouxEngine(RouxProperties properties, RegionPack regionPack, RouxCounters counters,
            CryptoSink cryptoSink, RouxIdGenerator idGenerator) {
        this.properties = properties;
        this.regionPack = regionPack;
        this.counters = counters;
        this.cryptoSink = cryptoSink;
        this.idGenerator = idGenerator;
        this.mapLock = new ReentrantLock();
        this.regions = new LinkedHashMap<String, Region>();
        this.ramRegions = new HashSet<String>();
    }

    /**
     * The method opens a memory-only region.
     * A null id or an empty id returns a refused {@link OpenResult} with {@code invalid region}.
     * The id is added to the memory-only set before the region is created.
     * An id that is already open returns a new-kind result for that region's UUID.
     * A new id is allocated with a capacity hint of {@code 0}.
     * A missing region after that allocation returns a refused result with {@code region not found}.
     * The result kind is new for both a fresh id and an id that was already open.
     * The method does not take a pack lock and does not create a spill directory.
     *
     * // @aa-sdd G-08 G-09 Q-S1-1 aetheris-stages-1-3.finalize.md
     *
     * @param regionId The region id is the conversation id to open.
     * @return The result is a new-kind open, or a refusal.
     */
    public OpenResult openRam(String regionId) {
        if (regionId == null || regionId.length() == 0) {
            return OpenResult.refused("invalid region");
        }
        mapLock.lock();
        try {
            ramRegions.add(regionId);
            Region existing = regions.get(regionId);
            if (existing != null) {
                return OpenResult.neu(existing.regionUuid);
            }
        } finally {
            mapLock.unlock();
        }
        allocateRegion(regionId, 0);
        Region ram = require(regionId);
        if (ram == null) {
            return OpenResult.refused("region not found");
        }
        return OpenResult.neu(ram.regionUuid);
    }

    /**
     * The method creates a region or returns the one already stored.
     * A null id or an empty id fails with {@link AllocationStatus#OUT_OF_ADDRESS_SPACE} and {@code regionId required}.
     * An id that is already stored succeeds and describes that region.
     * A new region uses {@link #pageBytes()}, increments the allocated-page counter, and loads the head prefix.
     * The capacity hint is not read.
     *
     * @param regionId The region id is the conversation id.
     * @param capacityHintBytes The hint is accepted and not used.
     * @return The result is success with a {@link RegionDescriptor}, or the empty-id failure.
     */
    @Override
    public AllocationResult allocateRegion(String regionId, long capacityHintBytes) {
        if (regionId == null || regionId.length() == 0) {
            return new AllocationResult(false, null, AllocationStatus.OUT_OF_ADDRESS_SPACE, "regionId required");
        }
        boolean created = false;
        AllocationResult result;
        mapLock.lock();
        try {
            Region existing = regions.get(regionId);
            if (existing != null) {
                result = new AllocationResult(true, describe(existing), AllocationStatus.SUCCESS, "");
            } else {
                Region region = new Region(regionId, pageBytes());
                regions.put(regionId, region);
                counters.pagesAllocated().incrementAndGet();
                loadHead(region);
                result = new AllocationResult(true, describe(region), AllocationStatus.SUCCESS, "");
                created = true;
            }
        } finally {
            mapLock.unlock();
        }
        if (created) {
            noteOpened(regionId);
        }
        return result;
    }

    /**
     * The method rotates the hot page.
     * A missing region fails with {@link AllocationStatus#OUT_OF_ADDRESS_SPACE} and {@code region not found}.
     * A failed rotation returns {@link AllocationStatus#SEVERE_FRAGMENTATION}, the rotation message, and the current descriptor.
     * The previous hot page remains when rotation fails.
     * A successful rotation returns {@link AllocationStatus#SUCCESS} and the new descriptor.
     *
     * @param regionId The region id is the conversation id.
     * @return The result describes the region after the attempt.
     */
    @Override
    public AllocationResult growHotPage(String regionId) {
        Region region = require(regionId);
        if (region == null) {
            return new AllocationResult(false, null, AllocationStatus.OUT_OF_ADDRESS_SPACE, "region not found");
        }
        region.lock.lock();
        try {
            OperationResult grown = rotateHot(region);
            if (!grown.isSuccess()) {
                return new AllocationResult(false, describe(region), AllocationStatus.SEVERE_FRAGMENTATION,
                        grown.getMessage());
            }
            return new AllocationResult(true, describe(region), AllocationStatus.SUCCESS, "");
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method closes a region.
     * A missing region returns success with {@code already closed}.
     * A pinned region returns {@link OperationStatus#BLOCKED_BY_PIN} and {@code region pinned} when the force flag is false.
     * Any other region is closed through {@link #persistClose}.
     *
     * @param regionId The region id is the conversation id.
     * @param forceIfPinned The flag closes a pinned region when it is true.
     * @return The result is success, a pin refusal, or the close result.
     */
    @Override
    public OperationResult safeFreeRegion(String regionId, boolean forceIfPinned) {
        Region region = require(regionId);
        if (region == null) {
            return OperationResult.ok("already closed");
        }
        if (region.regionPinned && !forceIfPinned) {
            return OperationResult.fail(OperationStatus.BLOCKED_BY_PIN, "region pinned");
        }
        return persistClose(regionId);
    }

    /**
     * The method refuses defragment.
     * The region id is not read.
     * The status is {@link OperationStatus#UNSUPPORTED}.
     * The message is {@code defragment not implemented in v1}.
     *
     * @param regionId The region id is accepted and not used.
     * @return The result is the unsupported failure.
     */
    @Override
    public OperationResult defragment(String regionId) {
        return OperationResult.fail(OperationStatus.UNSUPPORTED, "defragment not implemented in v1");
    }

    /**
     * The method stores the region pin flag.
     * A missing region returns without a change.
     * The flag is written while the region lock is held.
     *
     * @param regionId The region id is the conversation id.
     * @param pinned The flag is the pin value to store.
     */
    @Override
    public void setPinned(String regionId, boolean pinned) {
        Region region = require(regionId);
        if (region == null) {
            return;
        }
        region.lock.lock();
        try {
            region.regionPinned = pinned;
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method appends one message frame on the hot page.
     * The region is allocated first, and the capacity hint is {@code 0}.
     * A missing region returns {@link OperationStatus#NOT_FOUND} and {@code region not found}.
     * The payload is encoded by {@link ToonCodec}.
     * A message that needs more than 32 chunks returns {@link OperationStatus#CORRUPTED_SEGMENT} and {@code more than 32 chunks}.
     * One frame stores chunk word 1.
     * A new {@link RouxId} is minted for the message.
     * The wait for the region lock is added to the lock-wait counter.
     *
     * A hot page that cannot hold the frame is rotated first.
     * A failed rotation is returned as that failure.
     * A page that is still too small returns {@link OperationStatus#CORRUPTED_SEGMENT} and {@code hot page too small}.
     * The frame type is {@link FrameType#MSG}.
     * The message id is the region's next id, and that id then advances by one.
     * The first frame on a page stores the first message id.
     * The last message id on the page becomes this message id.
     * The index row and the identity map both record the new id.
     * The appended-frame counter and the index-entry counter each advance by one.
     * Durability {@code append} with an enabled pack publishes the hot page.
     * The page pin is released before the method returns.
     * Success uses an empty message.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param regionId The region id is the conversation id.
     * @param role The role is the role byte stored in the frame.
     * @param timestampEpochMs The time is the epoch milliseconds stored in the frame.
     * @param tokenCount The token count is the number stored in the frame.
     * @param text The text is the message body.
     * @return The result is success, or the failure from allocation, size, or rotation.
     */
    @Override
    public OperationResult appendMessage(String regionId, byte role, long timestampEpochMs, int tokenCount,
            String text) {
        allocateRegion(regionId, 0);
        Region region = require(regionId);
        if (region == null) {
            return OperationResult.fail(OperationStatus.NOT_FOUND, "region not found");
        }
        byte[] payload = ToonCodec.encode(role, timestampEpochMs, tokenCount, text);
        long waitStart = System.nanoTime();
        region.lock.lock();
        counters.lockWaitNanos().addAndGet(System.nanoTime() - waitStart);
        try {
            return writeChunks(region, region.nextMessageId, role, timestampEpochMs, tokenCount, null, payload, true);
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method cuts one encoded payload into frames.
     * The first frame is a message frame and stores the chunk count.
     * Each later frame is a continuation and stores its place.
     * A count above 32 is refused before any byte of this message is written.
     * One frame stores chunk word 1.
     * The index row is written when the message frame is written.
     * A fresh message advances the next id once and stores the identity.
     * An edit retargets the row and does not advance the next id.
     *
     * @param region The region receives the frames.
     * @param messageId The message id is the sequence shared by every chunk.
     * @param role The role is the role byte stored in every chunk.
     * @param timestamp The time is the epoch milliseconds stored in every chunk.
     * @param tokenCount The token count is the number stored in every chunk.
     * @param rouxId The identity is the stored identity for an edit, or null for a fresh message.
     * @param payload The payload is the one encoding of the message.
     * @param fresh The flag is true when the message is new and false when the message is an edit.
     * @return The result is success, or the size or rotation failure.
     */
    // @aa-sdd PK-1468 docs/specs/java/org/roux/roux.spec.md
    private OperationResult writeChunks(Region region, long messageId, byte role, long timestamp, int tokenCount,
            RouxId rouxId, byte[] payload, boolean fresh) {
        int[] lengths = chunkLengths(payload, maxFrameBytes());
        if (lengths == null) {
            return OperationResult.fail(OperationStatus.CORRUPTED_SEGMENT, "hot page too small");
        }
        if (lengths.length > 32) {
            return OperationResult.fail(OperationStatus.CORRUPTED_SEGMENT, "more than 32 chunks");
        }
        RouxId id = rouxId;
        if (fresh) {
            id = idGenerator.next();
        }
        int at = 0;
        int msgPage = -1;
        int lastPageId = -1;
        for (int i = 0; i < lengths.length; i++) {
            int n = lengths[i];
            byte[] slice = new byte[n];
            if (n > 0) {
                System.arraycopy(payload, at, slice, 0, n);
            }
            int total = FrameHeader.totalLength(n);
            if (region.hot.remaining() < total) {
                OperationResult rotated = rotateHot(region);
                if (!rotated.isSuccess()) {
                    return rotated;
                }
            }
            if (region.hot.remaining() < total) {
                return OperationResult.fail(OperationStatus.CORRUPTED_SEGMENT, "hot page too small");
            }
            int offset = region.hot.usedBytes();
            Page page = region.hot;
            region.pins.pin(page.getPageId());
            try {
                page.beginWrite();
                byte type = i == 0 ? FrameType.MSG : FrameType.CONT;
                FrameHeader.write(page.getSegment(), offset, total, type, role, messageId, timestamp, tokenCount, id,
                        slice);
                int word = i == 0 ? lengths.length : i;
                FrameHeader.setChunk(page.getSegment(), offset, word);
                int used = offset + total;
                PageHeader.setUsedBytes(page.getSegment(), used);
                PageHeader.setFrameCount(page.getSegment(), PageHeader.frameCount(page.getSegment()) + 1);
                if (PageHeader.frameCount(page.getSegment()) == 1) {
                    PageHeader.setFirstId(page.getSegment(), messageId);
                }
                if (fresh) {
                    PageHeader.setLastId(page.getSegment(), messageId);
                } else {
                    long last = PageHeader.lastId(page.getSegment());
                    if (messageId >= last) {
                        PageHeader.setLastId(page.getSegment(), messageId);
                    }
                }
                page.endWrite();
            } finally {
                region.pins.unpin(page.getPageId());
            }
            if (i == 0) {
                if (fresh) {
                    region.index.append(messageId, page.getPageId(), offset, total, tokenCount, timestamp, role,
                            FrameType.MSG, (short) 0, id);
                    region.byId.put(id, Long.valueOf(messageId));
                    region.nextMessageId = messageId + 1;
                    counters.indexEntries().incrementAndGet();
                } else {
                    region.index.retarget(messageId, page.getPageId(), offset, total, tokenCount);
                }
                msgPage = page.getPageId();
            }
            lastPageId = page.getPageId();
            counters.framesAppended().incrementAndGet();
            at += n;
        }
        int storedLast = lastPageId == msgPage ? 0 : lastPageId;
        region.index.setLastPage(messageId, storedLast);
        recountUsed();
        if ("append".equals(properties.normalizedDurability()) && regionPack.isEnabled()) {
            checkpointHot(region);
        }
        return OperationResult.ok("");
    }

    /**
     * The method lists the payload length of each chunk.
     * Each chunk takes the most bytes that fit in one frame.
     * A chunk that cannot be formed returns null.
     * A count above 32 returns an array of length 33 and does not list the bytes.
     *
     * @param payload The payload is the one encoding.
     * @param maxFrame The max frame is the cap of one chunk.
     * @return The lengths are the chunk sizes, or null when a chunk cannot be formed.
     */
    private int[] chunkLengths(byte[] payload, int maxFrame) {
        int room = maxFrame - FrameHeader.SIZE;
        while (room >= 0 && FrameHeader.totalLength(room) > maxFrame) {
            room--;
        }
        if (room < 0 || FrameHeader.totalLength(0) > maxFrame) {
            return null;
        }
        if (payload.length == 0) {
            int[] one = new int[1];
            one[0] = 0;
            return one;
        }
        if (room == 0) {
            return null;
        }
        int count = (payload.length + room - 1) / room;
        if (count > 32) {
            return new int[33];
        }
        int[] lengths = new int[count];
        int at = 0;
        for (int i = 0; i < count; i++) {
            int n = payload.length - at;
            if (n > room) {
                n = room;
            }
            lengths[i] = n;
            at += n;
        }
        return lengths;
    }

    /**
     * The method drops the frames and keeps the region.
     * A missing region returns without a change.
     * The pages are closed, the index is reset, and the identity map is cleared.
     * The next message id becomes {@code 1}.
     * A new hot page {@code 0} is stored, and the next page id becomes {@code 1}.
     * The allocated-page counter increments.
     * A memory-only process recounts the used bytes and returns.
     * Any other process wipes the pack, writes the manifest, and then recounts.
     *
     * @param regionId The region id is the conversation id.
     */
    @Override
    public void clearRegion(String regionId) {
        Region region = require(regionId);
        if (region == null) {
            return;
        }
        region.lock.lock();
        try {
            closePages(region);
            region.index.reset();
            region.byId.clear();
            region.nextMessageId = 1;
            region.nextPageId = 0;
            region.hot = new Page(0, pageBytes());
            region.pages.clear();
            region.pages.put(Integer.valueOf(0), region.hot);
            region.nextPageId = 1;
            region.clip = null;
            counters.pagesAllocated().incrementAndGet();
            if (properties.isRamOnly()) {
                recountUsed();
                return;
            }
            regionPack.wipePack(regionId);
            try {
                Files.deleteIfExists(regionPack.packDir(regionId).resolve(ClipRecord.FILE_NAME));
            } catch (IOException ignored) {
                // a leftover clip is unread on the next open when the checksum fails
            }
            writeManifest(region);
            recountUsed();
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method tries to take the region lock.
     * A missing region returns null.
     * The wait is {@code timeoutMs} milliseconds.
     * A timeout returns null.
     * An interrupt returns null and sets the interrupt status on this thread.
     * The token id is a random UUID string.
     * The level is stored on the token and does not change the branch.
     * The token is stored as the operator lock.
     * The region lock stays held until {@link #releaseLock}.
     *
     * @param regionId The region id is the conversation id.
     * @param level The level is stored on the token.
     * @param timeoutMs The timeout is the wait in milliseconds.
     * @return The token is the held lock, or null when the lock was not taken.
     */
    @Override
    public LockToken tryAcquireLock(String regionId, int level, long timeoutMs) {
        Region region = require(regionId);
        if (region == null) {
            return null;
        }
        try {
            if (!region.lock.tryLock(timeoutMs, java.util.concurrent.TimeUnit.MILLISECONDS)) {
                return null;
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            return null;
        }
        LockToken token = new LockToken(UUID.randomUUID().toString(), regionId, level, System.currentTimeMillis());
        region.operatorLock = token;
        return token;
    }

    /**
     * The method releases an operator lock.
     * A null token returns false.
     * A missing region returns false.
     * A missing operator lock, or a lock id that does not match, returns false.
     * A match clears the operator lock.
     * The region lock is unlocked when this thread holds it.
     * A match returns true.
     *
     * @param token The token is the lock to release.
     * @return The flag is true when that lock was released.
     */
    @Override
    public boolean releaseLock(LockToken token) {
        if (token == null) {
            return false;
        }
        Region region = require(token.getRegionId());
        if (region == null) {
            return false;
        }
        if (region.operatorLock == null || !region.operatorLock.getLockId().equals(token.getLockId())) {
            return false;
        }
        region.operatorLock = null;
        if (region.lock.isHeldByCurrentThread()) {
            region.lock.unlock();
        }
        return true;
    }

    /**
     * The method returns the current telemetry snapshot.
     *
     * @return The snapshot is {@link RouxCounters#snapshot}.
     */
    @Override
    public MemoryTelemetry getTelemetry() {
        return counters.snapshot();
    }

    /**
     * The method lists every open region.
     * The copy is taken while the map lock is held.
     * Each entry is a {@link RegionDescriptor}.
     *
     * @return The list is the open regions in insertion order.
     */
    @Override
    public List<RegionDescriptor> listRegions() {
        mapLock.lock();
        try {
            List<RegionDescriptor> list = new ArrayList<RegionDescriptor>();
            for (Region region : regions.values()) {
                list.add(describe(region));
            }
            return list;
        } finally {
            mapLock.unlock();
        }
    }

    /**
     * The method describes one region.
     * A missing region returns null.
     *
     * @param regionId The region id is the conversation id.
     * @return The descriptor is that region, or null.
     */
    @Override
    public RegionDescriptor getRegion(String regionId) {
        Region region = require(regionId);
        if (region == null) {
            return null;
        }
        return describe(region);
    }

    /**
     * The method copies bytes from a mapped page into a destination.
     * A null address or a null destination returns without a copy.
     * A missing region returns without a copy.
     * A missing page, or a page with no segment, increments the stale-view counter and returns.
     * The copy length is the address length.
     * A length larger than the destination is cut to the destination size.
     * The copy starts at the address offset and lands at offset {@code 0} in the destination.
     *
     * @param address The address names the region, the page, the offset, and the length.
     * @param dest The destination receives the copied bytes.
     */
    @Override
    public void readRaw(MemoryAddress address, MemorySegment dest) {
        if (address == null || dest == null) {
            return;
        }
        Region region = require(address.getRegionId());
        if (region == null) {
            return;
        }
        Page page = region.pages.get(Integer.valueOf(address.getPageId()));
        if (page == null || page.getSegment() == null) {
            counters.staleViews().incrementAndGet();
            return;
        }
        long len = address.getLength();
        if (len > dest.byteSize()) {
            len = dest.byteSize();
        }
        MemorySegment.copy(page.getSegment(), address.getOffset(), dest, 0, len);
    }

    /**
     * The method checks the hot page magic.
     * A missing region is unhealthy and the message is {@code not found}.
     * A hot page with a segment and no magic is unhealthy and the message is {@code bad magic}.
     * Every other region is healthy and the message is empty.
     * Spilled pages are not scanned.
     *
     * @param regionId The region id is the conversation id.
     * @return The report is healthy or unhealthy for that region.
     */
    @Override
    public HealthReport verifyIntegrity(String regionId) {
        Region region = require(regionId);
        if (region == null) {
            return new HealthReport(regionId, false, "not found");
        }
        if (region.hot != null && region.hot.getSegment() != null && !PageHeader.hasMagic(region.hot.getSegment())) {
            return new HealthReport(regionId, false, "bad magic");
        }
        return new HealthReport(regionId, true, "");
    }

    /**
     * The method does not capture a snapshot.
     * The options and the label are not read.
     *
     * @param options The options are accepted and not used.
     * @param label The label is accepted and not used.
     * @return The handle is null.
     */
    @Override
    public SnapshotHandle createAtomicSnapshot(SnapshotOptions options, String label) {
        return null;
    }

    /**
     * The method refuses a snapshot restore.
     * The handle is not read.
     * The status is {@link OperationStatus#UNSUPPORTED}.
     * The message is {@code snapshot restore not implemented in v1}.
     *
     * @param handle The handle is accepted and not used.
     * @return The result is the unsupported failure.
     */
    @Override
    public OperationResult restoreSnapshot(SnapshotHandle handle) {
        return OperationResult.fail(OperationStatus.UNSUPPORTED, "snapshot restore not implemented in v1");
    }

    /**
     * The method refuses a heap dump.
     * The destination path is not read.
     * The status is {@link OperationStatus#UNSUPPORTED}.
     * The message is {@code heap dump not implemented in v1}.
     *
     * @param destinationPath The path is accepted and not used.
     * @return The result is the unsupported failure.
     */
    @Override
    public OperationResult exportHeapDump(Path destinationPath) {
        return OperationResult.fail(OperationStatus.UNSUPPORTED, "heap dump not implemented in v1");
    }

    /**
     * The method closes every region.
     * The call is {@link #shutdown} with {@code true}.
     */
    @PreDestroy
    public void destroy() {
        shutdown(true);
    }

    /**
     * The method closes every open region.
     * The ids are copied while the map lock is held.
     * Each id is then passed to {@link #persistClose}.
     * The force flag is not read.
     * A pin does not stop this close.
     *
     * @param forceUnmap The flag is accepted and not used.
     */
    @Override
    public void shutdown(boolean forceUnmap) {
        List<String> ids = new ArrayList<String>();
        mapLock.lock();
        try {
            for (Region region : regions.values()) {
                ids.add(region.id);
            }
        } finally {
            mapLock.unlock();
        }
        for (int i = 0; i < ids.size(); i++) {
            persistClose(ids.get(i));
        }
    }

    /**
     * The method reports whether the process stays in memory.
     * The answer comes from {@link RouxProperties#isRamOnly}.
     *
     * @return The flag is true when the settings do not use a pack.
     */
    public boolean isRamOnly() {
        return properties.isRamOnly();
    }

    /**
     * The method reports whether an id was opened through {@link #openRam}.
     * A null id returns false.
     * The check reads the memory-only set while the map lock is held.
     *
     * @param regionId The region id is the conversation id.
     * @return The flag is true when {@link #openRam} recorded that id.
     */
    public boolean isRamRegion(String regionId) {
        if (regionId == null) {
            return false;
        }
        mapLock.lock();
        try {
            return ramRegions.contains(regionId);
        } finally {
            mapLock.unlock();
        }
    }

    /**
     * The method opens a region or restores one from the pack.
     * A null id or an empty id is refused with {@code invalid username}.
     * A memory-only process allocates the id and returns a new-kind result.
     * A missing region after that allocation is refused with {@code region not found}.
     *
     * An id that is already in the region map is refused with {@code session already open for } plus the id.
     * A pack lock that is not acquired uses that same refusal.
     * A new region keeps the pack lock.
     * No pack file and no manifest file allocates the id and returns a new-kind result.
     * A failed allocation unlocks the pack and is refused with {@code region not found}.
     *
     * Restore turned off unlocks the pack.
     * The refusal is {@code roux restore disabled; leftover pack not attached; delete } plus the directory plus {@code  or enable restore}.
     * A manifest file without a pack file uses the incomplete-pack refusal.
     * That refusal is {@code roux pack incomplete or corrupt; delete } plus the directory plus {@code  or revert yaml}.
     * A manifest that cannot be read, a null manifest, a format version other than {@link PageHeader#VERSION}, or an owner that is not empty and not this id uses that same refusal.
     * A page size that differs from {@link #pageBytes} is refused with {@code roux pack page-bytes mismatch (manifest } plus the manifest size plus {@code , yaml } plus the engine size plus {@code )}.
     *
     * A pack with no manifest is restored by {@link #restoreFromPack} with a null manifest.
     * A matching pack and manifest is restored with that manifest.
     * A refused restore unlocks the pack.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     * // @aa-sdd PK-1401 docs/specs/java/org/roux/roux.spec.md
     *
     * @param regionId The region id is the conversation id.
     * @return The result is new, restored, or refused.
     */
    public OpenResult openRegion(String regionId) {
        if (regionId == null || regionId.length() == 0) {
            return OpenResult.refused("invalid username");
        }
        if (properties.isRamOnly()) {
            allocateRegion(regionId, 0);
            Region ram = require(regionId);
            if (ram == null) {
                return OpenResult.refused("region not found");
            }
            return OpenResult.neu(ram.regionUuid);
        }
        mapLock.lock();
        boolean live;
        try {
            live = regions.containsKey(regionId);
        } finally {
            mapLock.unlock();
        }
        if (live) {
            return OpenResult.refused("session already open for " + regionId);
        }
        if (!regionPack.tryLock(regionId)) {
            return OpenResult.refused("session already open for " + regionId);
        }
        boolean pack = regionPack.packExists(regionId);
        boolean manFile = regionPack.manifestExists(regionId);
        Path dir = regionPack.packDir(regionId);
        if (!pack && !manFile) {
            allocateRegion(regionId, 0);
            Region created = require(regionId);
            if (created == null) {
                regionPack.unlock(regionId);
                return OpenResult.refused("region not found");
            }
            loadClip(created);
            return OpenResult.neu(created.regionUuid);
        }
        if (!properties.sessionRestore()) {
            regionPack.unlock(regionId);
            return OpenResult.refused("roux restore disabled; leftover pack not attached; delete " + dir
                    + " or enable restore");
        }
        if (!pack && manFile) {
            regionPack.unlock(regionId);
            return OpenResult.refused("roux pack incomplete or corrupt; delete " + dir + " or revert yaml");
        }
        RegionManifest man = null;
        if (manFile) {
            try {
                man = RegionManifest.read(dir);
            } catch (IOException ex) {
                regionPack.unlock(regionId);
                return OpenResult.refused("roux pack incomplete or corrupt; delete " + dir + " or revert yaml");
            }
            if (man == null) {
                regionPack.unlock(regionId);
                return OpenResult.refused("roux pack incomplete or corrupt; delete " + dir + " or revert yaml");
            }
            if (man.getPageBytes() != pageBytes()) {
                regionPack.unlock(regionId);
                return OpenResult.refused("roux pack page-bytes mismatch (manifest " + man.getPageBytes()
                        + ", yaml " + pageBytes() + ")");
            }
            if (man.getFormatVersion() != PageHeader.VERSION) {
                regionPack.unlock(regionId);
                return OpenResult.refused("roux pack incomplete or corrupt; delete " + dir + " or revert yaml");
            }
            if (man.getOwner() != null && man.getOwner().length() > 0 && !man.getOwner().equals(regionId)) {
                regionPack.unlock(regionId);
                return OpenResult.refused("roux pack incomplete or corrupt; delete " + dir + " or revert yaml");
            }
        }
        OpenResult scanned = restoreFromPack(regionId, man);
        if (scanned.getKind() == OpenResult.REFUSED) {
            regionPack.unlock(regionId);
        }
        return scanned;
    }

    /**
     * The method closes one region.
     * A missing region returns success with {@code already closed}.
     * A region that is already closed returns that same success.
     * A memory-only process, or an id opened through {@link #openRam}, destroys the region and removes the id.
     * That path returns success with {@code closed}.
     *
     * Persist on publishes the hot page, writes the manifest, saves the head prefix, destroys the region, and unlocks the pack.
     * The files stay.
     * The folder bind is cleared after those writes, so the next open uses the username directory.
     * Persist off destroys the region and deletes the username directory.
     * A named sibling stays.
     * Both disk paths mark the region closed, remove the id, and return success with {@code closed}.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param regionId The region id is the conversation id.
     * @return The result is success with {@code already closed} or {@code closed}.
     */
    public OperationResult persistClose(String regionId) {
        Region region = require(regionId);
        if (region == null) {
            return OperationResult.ok("already closed");
        }
        region.lock.lock();
        try {
            if (region.closed) {
                return OperationResult.ok("already closed");
            }
            if (properties.isRamOnly() || isRamRegion(regionId)) {
                destroyRegion(region);
                region.closed = true;
                mapLock.lock();
                try {
                    regions.remove(regionId);
                } finally {
                    mapLock.unlock();
                }
                return OperationResult.ok("closed");
            }
            if (properties.sessionPersist()) {
                checkpointHot(region);
                writeManifest(region);
                saveHead(region);
                destroyRegion(region);
                regionPack.bindFolder(regionId, null);
                regionPack.unlock(regionId);
            } else {
                destroyRegion(region);
                regionPack.bindFolder(regionId, null);
                regionPack.deleteRegion(regionId);
            }
            region.closed = true;
            mapLock.lock();
            try {
                regions.remove(regionId);
            } finally {
                mapLock.unlock();
            }
            return OperationResult.ok("closed");
        } finally {
            if (region.lock.isHeldByCurrentThread()) {
                region.lock.unlock();
            }
        }
    }

    /**
     * The method rebuilds a region from {@code pages.bin}.
     * An I/O failure, or a slot count below zero, is refused with the incomplete-pack sentence.
     * A null manifest and fewer than one slot is refused with that sentence.
     * A null manifest uses the last accepted slot as the hot page and repairs the manifest.
     * Fewer slots than the manifest page count is refused.
     * More slots than the manifest page count uses the last accepted slot as the hot page and repairs the manifest.
     * An equal count uses the manifest hot page id.
     * A page count below one, or a hot page id outside that count, is refused.
     * An equal count does not repair the manifest.
     *
     * Each accepted slot is scanned for frames.
     * The hot slot is mapped and adopted as hot.
     * Every other slot stays spilled.
     * A manifest supplies the region UUID and the creation time.
     * The next message id becomes the index size plus one.
     * An id that is already open destroys the rebuilt region and is refused with {@code session already open for } plus the id.
     * The allocated-page counter adds the page count.
     * A repair writes the manifest.
     * The head prefix is loaded.
     * The result is restored with the index size and the region UUID.
     *
     * @param regionId The region id is the conversation id.
     * @param man The manifest is the stored record, or null when the pack has none.
     * @return The result is restored, or refused.
     */
    private OpenResult restoreFromPack(String regionId, RegionManifest man) {
        int taken;
        try {
            taken = walkSlots(regionId);
        } catch (IOException ex) {
            return OpenResult.refused("roux pack incomplete or corrupt; delete " + regionPack.packDir(regionId)
                    + " or revert yaml");
        }
        int hotId;
        boolean repair;
        String refuse = "roux pack incomplete or corrupt; delete " + regionPack.packDir(regionId)
                + " or revert yaml";
        if (taken < 0) {
            return OpenResult.refused(refuse);
        }
        if (man == null) {
            if (taken < 1) {
                return OpenResult.refused(refuse);
            }
            hotId = taken - 1;
            repair = true;
        } else if (man.getPageCount() < 1) {
            return OpenResult.refused(refuse);
        } else if (taken < man.getPageCount()) {
            return OpenResult.refused(refuse);
        } else if (taken > man.getPageCount()) {
            hotId = taken - 1;
            repair = true;
        } else {
            hotId = man.getHotPageId();
            if (man.getPageCount() < 1 || hotId < 0 || hotId >= man.getPageCount()) {
                return OpenResult.refused(refuse);
            }
            repair = false;
        }
        Region region = new Region(regionId, pageBytes());
        closePages(region);
        region.pages.clear();
        region.index.reset();
        region.byId.clear();
        region.nextMessageId = 1;
        region.nextPageId = taken;
        if (man != null) {
            region.regionUuid = man.getRegionUuid();
            region.createdEpochMs = man.getCreatedEpochMs();
        }
        Path pack = regionPack.packFile(regionId);
        try {
            for (int pageId = 0; pageId < taken; pageId++) {
                byte[] header = regionPack.readRange(regionId, pageId, 0, PageHeader.SIZE);
                Arena headerArena = Arena.ofConfined();
                int used;
                boolean magic;
                try {
                    MemorySegment hs = headerArena.allocate(PageHeader.SIZE, 8);
                    MemorySegment.copy(header, 0, hs, ValueLayout.JAVA_BYTE, 0, header.length);
                    used = PageHeader.usedBytes(hs);
                    magic = PageHeader.hasMagic(hs);
                } finally {
                    headerArena.close();
                }
                if (used < PageHeader.SIZE) {
                    used = PageHeader.SIZE;
                }
                if (used > pageBytes()) {
                    used = pageBytes();
                }
                byte[] slot = regionPack.readRange(regionId, pageId, 0, used);
                if (pageId == hotId) {
                    Page hot = new Page(pageId, pageBytes());
                    hot.mapFromBytes(slot);
                    hot.adoptAsHot();
                    hot.setFile(pack);
                    region.hot = hot;
                    region.pages.put(Integer.valueOf(pageId), hot);
                    if (magic) {
                        scanFrames(region, hot.getSegment(), pageId, used);
                    }
                } else {
                    Page spilled = Page.spilled(pageId, pageBytes(), pack);
                    region.pages.put(Integer.valueOf(pageId), spilled);
                    if (magic) {
                        Arena slotArena = Arena.ofConfined();
                        try {
                            MemorySegment seg = slotArena.allocate(used < 8 ? 8 : used, 8);
                            MemorySegment.copy(slot, 0, seg, ValueLayout.JAVA_BYTE, 0, slot.length);
                            scanFrames(region, seg, pageId, used);
                        } finally {
                            slotArena.close();
                        }
                    }
                }
            }
        } catch (IOException ex) {
            destroyRegion(region);
            return OpenResult.refused("roux pack incomplete or corrupt; delete " + regionPack.packDir(regionId)
                    + " or revert yaml");
        }
        if (region.hot == null) {
            destroyRegion(region);
            return OpenResult.refused("roux pack incomplete or corrupt; delete " + regionPack.packDir(regionId)
                    + " or revert yaml");
        }
        region.nextMessageId = region.index.size() + 1;
        mapLock.lock();
        try {
            if (regions.containsKey(regionId)) {
                destroyRegion(region);
                return OpenResult.refused("session already open for " + regionId);
            }
            regions.put(regionId, region);
        } finally {
            mapLock.unlock();
        }
        counters.pagesAllocated().addAndGet(region.pages.size());
        recountUsed();
        if (repair) {
            writeManifest(region);
        }
        loadHead(region);
        region.restored = true;
        noteOpened(regionId);
        loadClip(region);
        return OpenResult.restored(region.index.size(), region.regionUuid);
    }

    /**
     * The method counts page headers in the pack from page {@code 0}.
     * The file length is not the count.
     * A slot whose header does not fit in the file returns that page id when an earlier slot had the page magic.
     * A file whose slots have no page magic returns {@code 0}.
     * A header without the page magic is skipped while a later slot still fits.
     * Magic whose version is not {@link PageHeader#VERSION}, or whose page id is not this slot, returns {@code -1}.
     * A slot offset that overflows returns {@code -1}.
     *
     * // @aa-sdd PK-1401 docs/specs/java/org/roux/roux.spec.md
     *
     * @param regionId The region id is the conversation id.
     * @return The count is the number of accepted slots, or {@code -1} when a header is refused.
     * @throws IOException The pack file could not be sized or read.
     */
    private int walkSlots(String regionId) throws IOException {
        long size = Files.size(regionPack.packFile(regionId));
        long pitch = pageBytes();
        int pageId = 0;
        boolean saw = false;
        while (pageId >= 0) {
            long at = (long) pageId * pitch;
            if (at < 0) {
                return -1;
            }
            if (size < at + PageHeader.SIZE) {
                if (!saw) {
                    return 0;
                }
                return pageId;
            }
            byte[] header = regionPack.readRange(regionId, pageId, 0, PageHeader.SIZE);
            Arena arena = Arena.ofConfined();
            try {
                MemorySegment seg = arena.allocate(PageHeader.SIZE, 8);
                MemorySegment.copy(header, 0, seg, ValueLayout.JAVA_BYTE, 0, header.length);
                if (!PageHeader.hasMagic(seg)) {
                    pageId++;
                    continue;
                }
                int version = seg.get(RouxLayout.SHORT_LE, 4) & 0xFFFF;
                if (version != PageHeader.VERSION || PageHeader.pageId(seg) != pageId) {
                    return -1;
                }
                saw = true;
            } finally {
                arena.close();
            }
            pageId++;
        }
        return -1;
    }

    /**
     * The method reads frames in one page.
     * A null segment returns without a change.
     * A length below the frame header, or a frame that passes the used bytes, stops this page.
     * A payload length below zero, or a payload that does not fit in the frame, stops this page.
     * A checksum mismatch stops this page.
     * A message id equal to the index size plus one is appended, and its identity is stored.
     * A next sequence whose type is {@link FrameType#CONT} stops this page.
     * A message id from {@code 1} through the index size, whose type is {@link FrameType#MSG}, retargets that row's page, offset, length, and token count.
     * A message id from {@code 1} through the index size, whose type is {@link FrameType#CONT}, leaves the row and the scan reads the next frame.
     * An existing continuation whose present identity differs from the row stops this page.
     * Any other message id stops this page.
     *
     * @param region The region receives the index rows.
     * @param seg The segment is the page bytes.
     * @param pageId The page id is stored on each accepted row.
     * @param used The used count is the last byte to read.
     */
    // @aa-sdd PK-1466 docs/specs/java/org/roux/roux.spec.md
    private void scanFrames(Region region, MemorySegment seg, int pageId, int used) {
        if (seg == null) {
            return;
        }
        long offset = PageHeader.SIZE;
        while (offset + FrameHeader.SIZE <= used) {
            int total = FrameHeader.length(seg, offset);
            if (total < FrameHeader.SIZE || offset + total > used) {
                break;
            }
            int payloadLen = FrameHeader.payloadLen(seg, offset);
            if (payloadLen < 0 || FrameHeader.SIZE + payloadLen > total) {
                break;
            }
            byte[] payload = new byte[payloadLen];
            MemorySegment.copy(seg, ValueLayout.JAVA_BYTE, offset + FrameHeader.SIZE, payload, 0, payloadLen);
            if (!FrameHeader.crcMatches(payload, FrameHeader.crc(seg, offset))) {
                break;
            }
            long seq = FrameHeader.messageId(seg, offset);
            int tokens = FrameHeader.tokens(seg, offset);
            if (seq == region.index.size() + 1L) {
                if (continuationStops(region, seg, offset, seq)) {
                    break;
                }
                RouxId rid = RouxId.read(seg, offset + FrameHeader.OFF_ID_HI);
                region.index.append(seq, pageId, (int) offset, total, tokens,
                        FrameHeader.timestamp(seg, offset), FrameHeader.role(seg, offset),
                        FrameHeader.type(seg, offset), (short) 0, rid);
                region.byId.put(rid, Long.valueOf(seq));
            } else if (seq >= 1L && seq <= region.index.size()
                    && FrameHeader.type(seg, offset) == FrameType.MSG) {
                region.index.retarget(seq, pageId, (int) offset, total, tokens);
            } else if (seq >= 1L && seq <= region.index.size()
                    && FrameHeader.type(seg, offset) == FrameType.CONT) {
                if (continuationStops(region, seg, offset, seq)) {
                    break;
                }
            } else {
                break;
            }
            offset += total;
        }
    }

    /**
     * The method decides whether a continuation stops the page.
     * A next sequence whose type is a continuation stops the page.
     * An absent identity is two zero longs and does not stop the page.
     * A present identity that differs from the row stops the page.
     *
     * @param region The region holds the index row.
     * @param seg The segment is the page bytes.
     * @param offset The offset is the first byte of the frame.
     * @param seq The sequence is the message id on the frame.
     * @return The flag is true when the scan stops.
     */
    // @aa-sdd PK-1468 docs/specs/java/org/roux/roux.spec.md
    private boolean continuationStops(Region region, MemorySegment seg, long offset, long seq) {
        byte type = FrameHeader.type(seg, offset);
        if (seq == region.index.size() + 1L) {
            return type == FrameType.CONT;
        }
        if (type != FrameType.CONT) {
            return false;
        }
        RouxId frameId = RouxId.read(seg, offset + FrameHeader.OFF_ID_HI);
        if (frameId.getHi() == 0L && frameId.getLo() == 0L) {
            return false;
        }
        IndexEntry row = region.index.get(seq);
        if (row == null || row.getRouxId() == null) {
            return true;
        }
        return !frameId.equals(row.getRouxId());
    }

    /**
     * The method writes the manifest for a region.
     * The owner is the region id.
     * The format version is {@link PageHeader#VERSION}.
     * The next message id is copied from the region.
     * The page count is the next page id.
     * The hot page id and the hot used bytes are copied from the hot page.
     * A missing hot page stores hot page id {@code 0} and zero used bytes.
     * An {@link IOException} is swallowed.
     *
     * @param region The region supplies the manifest fields.
     */
    private void writeManifest(Region region) {
        RegionManifest man = new RegionManifest();
        man.setPageBytes(pageBytes());
        man.setPageCount(region.nextPageId);
        man.setHotPageId(region.hot == null ? 0 : region.hot.getPageId());
        man.setHotUsedBytes(region.hot == null ? 0 : region.hot.usedBytes());
        man.setNextSeq(region.nextMessageId);
        man.setLastWriteEpochMs(System.currentTimeMillis());
        man.setCreatedEpochMs(region.createdEpochMs);
        man.setFormatVersion(PageHeader.VERSION);
        man.setRegionUuid(region.regionUuid);
        man.setOwner(region.id);
        try {
            RegionManifest.write(regionPack.packDir(region.id), man);
        } catch (IOException ignored) {
            // persist best-effort; pack bytes are already forced
        }
    }

    /**
     * The method returns the index size.
     * A missing region returns {@code 0}.
     *
     * @param regionId The region id is the conversation id.
     * @return The id is the index size, or {@code 0} when the region is missing.
     */
    public long lastMessageId(String regionId) {
        Region region = require(regionId);
        if (region == null) {
            return 0L;
        }
        return region.index.size();
    }

    /**
     * The method returns the last page id stored for one message.
     * A stored 0 means the message occupies the page the row already names.
     * A missing region returns 0.
     *
     * @param regionId The region id is the conversation id.
     * @param messageId The message id is the index key.
     * @return The id is the stored last page, or 0.
     */
    int lastPage(String regionId, long messageId) {
        Region region = require(regionId);
        if (region == null) {
            return 0;
        }
        return region.index.lastPage(messageId);
    }

    /**
     * The method counts index rows of one role.
     * A missing region returns {@code 0}.
     *
     * @param regionId The region id is the conversation id.
     * @param role The role is the role byte to count.
     * @return The count is the number of matching rows, or {@code 0} when the region is missing.
     */
    public int countByRole(String regionId, byte role) {
        Region region = require(regionId);
        if (region == null) {
            return 0;
        }
        return region.index.countByRole(role);
    }

    /**
     * The method decodes one message frame.
     * A missing region returns null.
     * A missing index row returns null.
     * A row whose type is not {@link FrameType#MSG} returns null.
     * Missing bytes, or a frame shorter than the header, return null.
     * A checksum mismatch increments the dirty-frame counter and returns null.
     * A chunk word above 1 walks forward, joins the payloads, and decodes once.
     * A stored 0 on a message frame is read as 1.
     *
     * @param regionId The region id is the conversation id.
     * @param messageId The message id is the index key.
     * @return The frame is the decoded message, or null.
     */
    public DecodedFrame readMessage(String regionId, long messageId) {
        Region region = require(regionId);
        if (region == null) {
            return null;
        }
        IndexEntry entry = region.index.get(messageId);
        if (entry == null) {
            return null;
        }
        if (entry.getType() != FrameType.MSG) {
            return null;
        }
        byte[] frameBytes = readFrameBytes(region, entry);
        if (frameBytes == null || frameBytes.length < FrameHeader.SIZE) {
            return null;
        }
        byte[] payload = FrameHeader.payloadFromHeap(frameBytes);
        int crc = ByteBuffer.wrap(frameBytes).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(FrameHeader.OFF_CRC);
        if (!FrameHeader.crcMatches(payload, crc)) {
            counters.dirtyFrames().incrementAndGet();
            return null;
        }
        int word = ByteBuffer.wrap(frameBytes).order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt(FrameHeader.OFF_CHUNK);
        int count = word == 0 ? 1 : word;
        if (count == 1) {
            return ToonCodec.decode(payload, entry.getMessageId(), entry.getRole(), entry.getTimestamp(),
                    entry.getTokenCount());
        }
        if (count < 1) {
            return null;
        }
        return walkMessage(region, entry, payload, count);
    }

    /**
     * The method walks the continuation frames of one message.
     * Each stepped frame is a continuation of the same sequence and the same identity.
     * The chunk word of each step is that step's number.
     * The payloads are joined and decoded once.
     * A short walk, a bad checksum, a wrong type, a wrong identity, or a wrong place returns null.
     *
     * @param region The region holds the pages.
     * @param entry The entry is the message row.
     * @param firstPayload The payload is the message frame already checked.
     * @param count The count is the number of frames, and it is greater than 1.
     * @return The frame is the decoded message, or null.
     */
    // @aa-sdd PK-1468 docs/specs/java/org/roux/roux.spec.md
    private DecodedFrame walkMessage(Region region, IndexEntry entry, byte[] firstPayload, int count) {
        java.io.ByteArrayOutputStream joined = new java.io.ByteArrayOutputStream();
        joined.write(firstPayload, 0, firstPayload.length);
        int pageId = entry.getPageId();
        int offset = entry.getOffset();
        int length = entry.getLength();
        for (int step = 1; step < count; step++) {
            int nextOffset = offset + length;
            int used = usedOn(region, pageId);
            if (used < 0 || nextOffset + FrameHeader.SIZE > used) {
                int nextPage = nextOf(region, pageId);
                if (nextPage == 0) {
                    return null;
                }
                pageId = nextPage;
                offset = PageHeader.SIZE;
            } else {
                offset = nextOffset;
            }
            byte[] header = frameAt(region, pageId, offset, FrameHeader.SIZE);
            if (header == null || header.length < FrameHeader.SIZE) {
                return null;
            }
            int total = ByteBuffer.wrap(header).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(FrameHeader.OFF_LENGTH);
            if (total < FrameHeader.SIZE) {
                return null;
            }
            byte[] frame = frameAt(region, pageId, offset, total);
            if (frame == null || frame.length < total) {
                return null;
            }
            Arena arena = Arena.ofConfined();
            try {
                long bytes = frame.length;
                long aligned = (bytes + 7L) & ~7L;
                if (aligned < 8L) {
                    aligned = 8L;
                }
                MemorySegment seg = arena.allocate(aligned, 8);
                MemorySegment.copy(frame, 0, seg, ValueLayout.JAVA_BYTE, 0, frame.length);
                byte[] piece = FrameHeader.payloadFromHeap(frame);
                int crc = ByteBuffer.wrap(frame).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(FrameHeader.OFF_CRC);
                if (!FrameHeader.crcMatches(piece, crc)) {
                    counters.dirtyFrames().incrementAndGet();
                    return null;
                }
                if (FrameHeader.type(seg, 0) != FrameType.CONT) {
                    return null;
                }
                if (FrameHeader.messageId(seg, 0) != entry.getMessageId()) {
                    return null;
                }
                if (FrameHeader.chunk(seg, 0) != step) {
                    return null;
                }
                RouxId frameId = RouxId.read(seg, FrameHeader.OFF_ID_HI);
                if (frameId.getHi() == 0L && frameId.getLo() == 0L) {
                    return null;
                }
                if (entry.getRouxId() == null || !frameId.equals(entry.getRouxId())) {
                    return null;
                }
                joined.write(piece, 0, piece.length);
                length = total;
            } finally {
                arena.close();
            }
        }
        return ToonCodec.decode(joined.toByteArray(), entry.getMessageId(), entry.getRole(), entry.getTimestamp(),
                entry.getTokenCount());
    }

    /**
     * The method returns the used-byte count of one page.
     * A mapped page reads the header in memory.
     * Any other page reads the header through the frame reader.
     *
     * @param region The region holds the page.
     * @param pageId The page id is the page to read.
     * @return The count is the used bytes, or -1 when the header is missing.
     */
    private int usedOn(Region region, int pageId) {
        Page page = region.pages.get(Integer.valueOf(pageId));
        if (page != null && page.getSegment() != null) {
            return PageHeader.usedBytes(page.getSegment());
        }
        byte[] header = frameAt(region, pageId, 0, PageHeader.SIZE);
        if (header == null || header.length < PageHeader.SIZE) {
            return -1;
        }
        return ByteBuffer.wrap(header).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(PageHeader.OFF_USED);
    }

    /**
     * The method returns the next page id stored on one page.
     * A mapped page reads the header in memory.
     * Any other page reads the header through the frame reader.
     *
     * @param region The region holds the page.
     * @param pageId The page id is the page to read.
     * @return The id is the follower, or 0 when the header is missing.
     */
    private int nextOf(Region region, int pageId) {
        Page page = region.pages.get(Integer.valueOf(pageId));
        if (page != null && page.getSegment() != null) {
            return PageHeader.nextPageId(page.getSegment());
        }
        byte[] header = frameAt(region, pageId, 0, PageHeader.SIZE);
        if (header == null || header.length < PageHeader.SIZE) {
            return 0;
        }
        return ByteBuffer.wrap(header).order(java.nio.ByteOrder.LITTLE_ENDIAN).getInt(PageHeader.OFF_NEXT_PAGE);
    }

    /**
     * The method copies one range out of a page.
     * The copy uses the same reader as a frame read.
     *
     * @param region The region holds the page.
     * @param pageId The page id is the page to read.
     * @param offset The offset is the first byte of the range.
     * @param length The length is the number of bytes to copy.
     * @return The bytes are the range, or null.
     */
    private byte[] frameAt(Region region, int pageId, int offset, int length) {
        IndexEntry seat = new IndexEntry(0L, pageId, offset, length, 0, 0L, (byte) 0, FrameType.MSG, (short) 0, null);
        return readFrameBytes(region, seat);
    }

    /**
     * The method returns message ids for one window.
     * A missing region returns a zero-length array.
     * An empty index returns a zero-length array.
     * The order is the pinned system ids, then the tail in ascending id order.
     *
     * A true pin flag uses {@link #pinnedSystemIds}.
     * A false pin flag pins nothing.
     * Pinned token counts are charged first.
     * A pinned id is left out of the tail.
     * A row that is missing or is not {@link FrameType#MSG} is skipped.
     * An assistant row takes the previous row when that id is not pinned and its role is {@link Role#USER}, {@link Role#TOOL}, or {@link Role#TOOL_CALL}.
     *
     * A {@code maxNonSystem} of zero or less means no message cap.
     * A {@code maxTokens} of zero or less means no token cap.
     * The first tail atom is kept even when it passes a cap.
     * A later atom that would pass the message cap is left out, and the scan stops.
     * A later atom that would pass the token cap is kept, and the scan then stops.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param regionId The region id is the conversation id.
     * @param maxNonSystem The cap is the non-system message limit, and zero or less means no message cap.
     * @param pinSystem The flag includes the system pin when it is true.
     * @param maxTokens The budget is the token limit, and zero or less means no token cap.
     * @return The ids are the pinned ids followed by the ascending tail.
     */
    public long[] windowIds(String regionId, int maxNonSystem, boolean pinSystem, int maxTokens) {
        Region region = require(regionId);
        if (region == null) {
            return new long[0];
        }
        int size = region.index.size();
        if (size == 0) {
            return new long[0];
        }
        long[] pinned = pinSystem ? pinnedSystemIds(region) : new long[0];
        int tokens = 0;
        for (int p = 0; p < pinned.length; p++) {
            IndexEntry sys = region.index.get(pinned[p]);
            if (sys != null) {
                tokens += sys.getTokenCount();
            }
        }
        ArrayList<Long> tail = new ArrayList<Long>();
        int nonSystem = 0;
        long id = size;
        while (id >= 1) {
            if (isPinned(pinned, id)) {
                id--;
                continue;
            }
            IndexEntry entry = region.index.get(id);
            if (entry == null || entry.getType() != FrameType.MSG) {
                id--;
                continue;
            }
            ArrayList<Long> atom = new ArrayList<Long>();
            int atomTokens = 0;
            int atomCount = 0;
            if (entry.getRole() == Role.ASSISTANT && id - 1 >= 1 && !isPinned(pinned, id - 1)) {
                IndexEntry prev = region.index.get(id - 1);
                if (prev != null && (prev.getRole() == Role.USER || prev.getRole() == Role.TOOL
                        || prev.getRole() == Role.TOOL_CALL)) {
                    atom.add(Long.valueOf(id - 1));
                    atom.add(Long.valueOf(id));
                    atomTokens = prev.getTokenCount() + entry.getTokenCount();
                    atomCount = 2;
                    id -= 2;
                }
            }
            if (atomCount == 0) {
                atom.add(Long.valueOf(id));
                atomTokens = entry.getTokenCount();
                atomCount = 1;
                id--;
            }
            if (maxNonSystem > 0 && nonSystem + atomCount > maxNonSystem && nonSystem > 0) {
                break;
            }
            boolean overTokens = maxTokens > 0 && tokens + atomTokens > maxTokens && nonSystem > 0;
            for (int a = atom.size() - 1; a >= 0; a--) {
                tail.add(atom.get(a));
            }
            tokens += atomTokens;
            nonSystem += atomCount;
            if (overTokens) {
                break;
            }
        }
        int outSize = tail.size() + pinned.length;
        long[] ids = new long[outSize];
        int pos = 0;
        for (int p = 0; p < pinned.length; p++) {
            ids[pos++] = pinned[p];
        }
        for (int i = tail.size() - 1; i >= 0; i--) {
            ids[pos++] = tail.get(i).longValue();
        }
        return ids;
    }

    /**
     * The method returns the window ids at or after one floor.
     * A floor of {@code 0} or less uses {@link #windowIds(String, int, boolean, int)}.
     * A pinned system id stays even when it is behind the floor.
     * A tail atom is kept only when every id in the atom is at or after the floor.
     * An atom that straddles the floor is left out.
     * The walk does not split that atom.
     *
     * @param regionId The region id is the conversation id.
     * @param maxNonSystem The cap is the non-system message limit, and zero or less means no message cap.
     * @param pinSystem The flag includes the system pin when it is true.
     * @param maxTokens The budget is the token limit, and zero or less means no token cap.
     * @param floorSeq The floor is the first eligible tail sequence.
     * @return The ids are the pinned ids followed by the ascending tail.
     */
    // @aa-sdd PK-1470 docs/specs/java/org/roux/roux.spec.md
    public long[] windowIds(String regionId, int maxNonSystem, boolean pinSystem, int maxTokens, long floorSeq) {
        if (floorSeq <= 0L) {
            return windowIds(regionId, maxNonSystem, pinSystem, maxTokens);
        }
        Region region = require(regionId);
        if (region == null) {
            return new long[0];
        }
        int size = region.index.size();
        if (size == 0) {
            return new long[0];
        }
        long[] pinned = pinSystem ? pinnedSystemIds(region) : new long[0];
        int tokens = 0;
        for (int p = 0; p < pinned.length; p++) {
            IndexEntry sys = region.index.get(pinned[p]);
            if (sys != null) {
                tokens += sys.getTokenCount();
            }
        }
        ArrayList<Long> tail = new ArrayList<Long>();
        int nonSystem = 0;
        long id = size;
        while (id >= 1) {
            if (isPinned(pinned, id)) {
                id--;
                continue;
            }
            IndexEntry entry = region.index.get(id);
            if (entry == null || entry.getType() != FrameType.MSG) {
                id--;
                continue;
            }
            ArrayList<Long> atom = new ArrayList<Long>();
            int atomTokens = 0;
            int atomCount = 0;
            if (entry.getRole() == Role.ASSISTANT && id - 1 >= 1 && !isPinned(pinned, id - 1)) {
                IndexEntry prev = region.index.get(id - 1);
                if (prev != null && (prev.getRole() == Role.USER || prev.getRole() == Role.TOOL
                        || prev.getRole() == Role.TOOL_CALL)) {
                    atom.add(Long.valueOf(id - 1));
                    atom.add(Long.valueOf(id));
                    atomTokens = prev.getTokenCount() + entry.getTokenCount();
                    atomCount = 2;
                    id -= 2;
                }
            }
            if (atomCount == 0) {
                atom.add(Long.valueOf(id));
                atomTokens = entry.getTokenCount();
                atomCount = 1;
                id--;
            }
            boolean underFloor = false;
            for (int a = 0; a < atom.size(); a++) {
                if (atom.get(a).longValue() < floorSeq) {
                    underFloor = true;
                    break;
                }
            }
            if (underFloor) {
                break;
            }
            if (maxNonSystem > 0 && nonSystem + atomCount > maxNonSystem && nonSystem > 0) {
                break;
            }
            boolean overTokens = maxTokens > 0 && tokens + atomTokens > maxTokens && nonSystem > 0;
            for (int a = atom.size() - 1; a >= 0; a--) {
                tail.add(atom.get(a));
            }
            tokens += atomTokens;
            nonSystem += atomCount;
            if (overTokens) {
                break;
            }
        }
        int outSize = tail.size() + pinned.length;
        long[] ids = new long[outSize];
        int pos = 0;
        for (int p = 0; p < pinned.length; p++) {
            ids[pos++] = pinned[p];
        }
        for (int i = tail.size() - 1; i >= 0; i--) {
            ids[pos++] = tail.get(i).longValue();
        }
        return ids;
    }

    /**
     * The method collects the system ids that stay in front.
     * The leading run is every opening {@link FrameType#MSG} row whose role is {@link Role#SYSTEM}.
     * An empty leading run uses the last system id when that id is above zero.
     * An index with no system row returns a zero-length array.
     *
     * @param region The region supplies the index.
     * @return The ids are the leading system run, or the last system id.
     */
    private static long[] pinnedSystemIds(Region region) {
        int size = region.index.size();
        ArrayList<Long> leading = new ArrayList<Long>();
        for (long id = 1; id <= size; id++) {
            IndexEntry entry = region.index.get(id);
            if (entry == null || entry.getType() != FrameType.MSG || entry.getRole() != Role.SYSTEM) {
                break;
            }
            leading.add(Long.valueOf(id));
        }
        if (leading.isEmpty()) {
            long last = region.index.lastSystemId();
            if (last > 0L) {
                leading.add(Long.valueOf(last));
            }
        }
        long[] ids = new long[leading.size()];
        for (int i = 0; i < leading.size(); i++) {
            ids[i] = leading.get(i).longValue();
        }
        return ids;
    }

    /**
     * The method reports whether an id is in the pinned array.
     * The search is linear.
     *
     * @param pinned The pinned ids are the system ids already chosen.
     * @param id The id is the candidate message id.
     * @return The flag is true when the id appears in the array.
     */
    private static boolean isPinned(long[] pinned, long id) {
        for (int i = 0; i < pinned.length; i++) {
            if (pinned[i] == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method writes every decoded message as a table.
     * A missing region returns an empty string.
     * A null decode is skipped.
     * A count of zero returns {@code messages[0]{r,ts,tok,x}:} and a newline.
     * Any other count is finished by {@link ToonCodec#finishTable}.
     *
     * @param regionId The region id is the conversation id.
     * @return The table is the encoded text, or an empty string when the region is missing.
     */
    public String exportToon(String regionId) {
        Region region = require(regionId);
        if (region == null) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        int count = 0;
        int size = region.index.size();
        for (long id = 1; id <= size; id++) {
            DecodedFrame frame = readMessage(regionId, id);
            if (frame == null) {
                continue;
            }
            ToonCodec.appendTableRow(out, Role.nameOf(frame.getRole()), frame.getTimestampEpochMs(),
                    frame.getTokenCount(), frame.getText());
            count++;
        }
        if (count == 0) {
            return "messages[0]{r,ts,tok,x}:\n";
        }
        return ToonCodec.finishTable(out, count);
    }

    /**
     * The method replaces the hot page.
     * An enabled cipher returns the refusal from {@link CryptoSink#assertDisabled}.
     * A pack that is enabled, a process that is not memory-only, and a region that is not memory-only publish the old page.
     * That path waits up to {@code 5000} milliseconds for the old page to be unpinned.
     * A page that stays pinned returns {@link OperationStatus#BLOCKED_BY_PIN} and {@code page pinned}.
     * The old page stores the next page id, is published, and is then unmapped.
     * The spill counters record the time, the used bytes, and one spilled page.
     * An {@link IOException} returns {@link OperationStatus#CORRUPTED_SEGMENT} and the exception message.
     *
     * Every other region checks the resident cap.
     * A resident count at or above {@link #residentPages} returns {@link OperationStatus#RESOURCE_BUSY} and {@code resident-pages cap reached and spill is disabled}.
     * A failed rotation does not replace the hot page.
     * Success stores a new hot page, advances the next page id, increments the allocated-page counter, and returns an empty message.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md spillover-semantics.md
     *
     * @param region The region supplies the hot page.
     * @return The result is success, or the cipher, pin, I/O, or cap failure.
     */
    private OperationResult rotateHot(Region region) {
        OperationResult crypto = cryptoSink.assertDisabled();
        if (!crypto.isSuccess()) {
            return crypto;
        }
        Page old = region.hot;
        if (regionPack.isEnabled() && !properties.isRamOnly() && !isRamRegion(region.id)) {
            if (!region.pins.waitUnpinned(old.getPageId(), 5000)) {
                return OperationResult.fail(OperationStatus.BLOCKED_BY_PIN, "page pinned");
            }
            if (old.getSegment() != null) {
                PageHeader.setNextPageId(old.getSegment(), region.nextPageId);
            }
            try {
                long t0 = System.nanoTime();
                regionPack.publishPage(region.id, old);
                counters.spillNanos().addAndGet(System.nanoTime() - t0);
                counters.spillBytes().addAndGet(old.usedBytes());
                counters.pagesSpilled().incrementAndGet();
                old.setFile(regionPack.packFile(region.id));
                old.unmapRam();
            } catch (IOException ex) {
                return OperationResult.fail(OperationStatus.CORRUPTED_SEGMENT, ex.getMessage());
            }
        } else {
            int resident = countResident(region);
            if (resident >= residentPages()) {
                return OperationResult.fail(OperationStatus.RESOURCE_BUSY,
                        "resident-pages cap reached and spill is disabled");
            }
            if (old.getSegment() != null) {
                PageHeader.setNextPageId(old.getSegment(), region.nextPageId);
            }
        }
        int newId = region.nextPageId;
        Page next = new Page(newId, pageBytes());
        region.nextPageId = newId + 1;
        region.hot = next;
        region.pages.put(Integer.valueOf(newId), next);
        counters.pagesAllocated().incrementAndGet();
        recountUsed();
        return OperationResult.ok("");
    }

    /**
     * The method reads one frame.
     * A missing page increments the stale-view counter and returns null.
     * The page is pinned for the read and unpinned before the return.
     * A mapped page is copied from its segment.
     * An unmapped page is brought in by {@link #maybePageIn} and then copied.
     * A page that stays unmapped is read from the pack.
     * An {@link IOException} increments the stale-view counter and returns null.
     *
     * @param region The region holds the page.
     * @param entry The entry names the page, the offset, and the length.
     * @return The bytes are the frame, or null when the page cannot be read.
     */
    private byte[] readFrameBytes(Region region, IndexEntry entry) {
        Page page = region.pages.get(Integer.valueOf(entry.getPageId()));
        if (page == null) {
            counters.staleViews().incrementAndGet();
            return null;
        }
        region.pins.pin(entry.getPageId());
        try {
            if (page.getSegment() != null) {
                int len = entry.getLength();
                byte[] buf = new byte[len];
                MemorySegment.copy(page.getSegment(), ValueLayout.JAVA_BYTE, entry.getOffset(), buf, 0, len);
                return buf;
            }
            try {
                maybePageIn(region, page);
                if (page.getSegment() != null) {
                    int len = entry.getLength();
                    byte[] buf = new byte[len];
                    MemorySegment.copy(page.getSegment(), ValueLayout.JAVA_BYTE, entry.getOffset(), buf, 0, len);
                    return buf;
                }
                return regionPack.readRange(region.id, entry.getPageId(), entry.getOffset(), entry.getLength());
            } catch (IOException ex) {
                counters.staleViews().incrementAndGet();
                return null;
            }
        } finally {
            region.pins.unpin(entry.getPageId());
        }
    }

    /**
     * The method releases a region's memory.
     * The pages are closed, the index is closed, and the identity map is cleared.
     * The pack channel for that id is closed.
     *
     * @param region The region is the state to release.
     */
    private void destroyRegion(Region region) {
        closePages(region);
        region.index.close();
        region.byId.clear();
        regionPack.closeChannel(region.id);
    }

    /**
     * The method closes every page in the region.
     * The page map is cleared.
     * The hot page is set to null.
     *
     * @param region The region holds the pages.
     */
    private void closePages(Region region) {
        for (Page page : region.pages.values()) {
            page.close();
        }
        region.pages.clear();
        region.hot = null;
    }

    /**
     * The method publishes the hot page into the pack.
     * A disabled pack returns without a write.
     * A memory-only process returns without a write.
     * Durability {@code hot-only} returns without a write.
     * A missing region returns without a write.
     * Any other region publishes through {@link #checkpointHot} while the region lock is held.
     * The page stays mapped.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param regionId The region id is the conversation id.
     */
    public void checkpoint(String regionId) {
        if (!regionPack.isEnabled() || properties.isRamOnly()) {
            return;
        }
        if ("hot-only".equals(properties.normalizedDurability())) {
            return;
        }
        Region region = require(regionId);
        if (region == null) {
            return;
        }
        region.lock.lock();
        try {
            checkpointHot(region);
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method finds the message id for a {@link RouxId}.
     * A missing region returns {@code -1}.
     * A null id returns {@code -1}.
     * An id that is not in the map returns {@code -1}.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param regionId The region id is the conversation id.
     * @param id The id is the two-long identity.
     * @return The message id is the stored seq, or {@code -1}.
     */
    public long findSeq(String regionId, RouxId id) {
        Region region = require(regionId);
        if (region == null || id == null) {
            return -1L;
        }
        Long seq = region.byId.get(id);
        return seq == null ? -1L : seq.longValue();
    }

    /**
     * The method inspects one index row.
     * A missing region returns null.
     * A missing index row returns null.
     * The residency is taken from the page before the bytes are read.
     * A missing page is recorded as {@code SPILLED}.
     * The text is filled only when the checksum matches and {@link ToonCodec#decode} returns a frame.
     * A short frame leaves the text empty and the checksum flag false.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param regionId The region id is the conversation id.
     * @param seq The seq is the message id.
     * @return The inspect record describes the row, or null.
     */
    public MessageInspect inspect(String regionId, long seq) {
        Region region = require(regionId);
        if (region == null) {
            return null;
        }
        IndexEntry entry = region.index.get(seq);
        if (entry == null) {
            return null;
        }
        Page page = region.pages.get(Integer.valueOf(entry.getPageId()));
        String res = page == null ? "SPILLED" : page.residency();
        byte[] frameBytes = readFrameBytes(region, entry);
        boolean crcOk = false;
        String text = "";
        if (frameBytes != null && frameBytes.length >= FrameHeader.SIZE) {
            byte[] payload = FrameHeader.payloadFromHeap(frameBytes);
            int crc = java.nio.ByteBuffer.wrap(frameBytes).order(java.nio.ByteOrder.LITTLE_ENDIAN)
                    .getInt(FrameHeader.OFF_CRC);
            crcOk = FrameHeader.crcMatches(payload, crc);
            if (crcOk) {
                DecodedFrame decoded = ToonCodec.decode(payload, entry.getMessageId(), entry.getRole(),
                        entry.getTimestamp(), entry.getTokenCount());
                if (decoded != null) {
                    text = decoded.getText();
                }
            }
        }
        return new MessageInspect(seq, entry.getRouxId(), entry.getRole(), entry.getTimestamp(), entry.getTokenCount(),
                entry.getType(), entry.getPageId(), entry.getOffset(), entry.getLength(), res, crcOk, text);
    }

    /**
     * The method returns one status line.
     * A missing region returns {@code pages=0}.
     * The line is {@code pages=} the page count, {@code hot=} the hot page id or {@code -1}, {@code spilled=} the spilled count, {@code resident=} the mapped count, {@code durability=} the normalized durability, and {@code seq=} the index size.
     * A spilled page is spilled and has no segment.
     * A resident page has a segment.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param regionId The region id is the conversation id.
     * @return The line is the status text.
     */
    public String memoryStatusLine(String regionId) {
        Region region = require(regionId);
        if (region == null) {
            return "pages=0";
        }
        int spilled = 0;
        int resident = 0;
        for (Page page : region.pages.values()) {
            if (page.isSpilled() && page.getSegment() == null) {
                spilled++;
            }
            if (page.getSegment() != null) {
                resident++;
            }
        }
        return "pages=" + region.pages.size()
                + " hot=" + (region.hot == null ? -1 : region.hot.getPageId())
                + " spilled=" + spilled
                + " resident=" + resident
                + " durability=" + properties.normalizedDurability()
                + " seq=" + region.index.size();
    }

    /**
     * The method publishes the hot page when it still has a segment.
     * A missing hot page returns without a write.
     * A hot page with no segment returns without a write.
     * An {@link IOException} is swallowed.
     * The page stays mapped.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param region The region supplies the hot page.
     */
    private void checkpointHot(Region region) {
        if (region.hot == null || region.hot.getSegment() == null) {
            return;
        }
        try {
            regionPack.publishPage(region.id, region.hot);
        } catch (IOException ignored) {
            // durability best-effort
        }
    }

    /**
     * The method maps a spilled page when the resident cap allows it.
     * A page that already has a segment returns.
     * At the cap, {@link #pageOutOldest} unmaps one other page first.
     * A count that is still at the cap returns, and the page stays on disk.
     * A used count of zero or less reads {@link RouxProperties#getPageBytes} bytes.
     * The bytes are mapped onto the page.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param region The region holds the page.
     * @param page The page is the slot to map.
     * @throws IOException The pack read failed.
     */
    private void maybePageIn(Region region, Page page) throws IOException {
        if (page.getSegment() != null) {
            return;
        }
        int cap = residentPages();
        int resident = countResident(region);
        if (resident >= cap) {
            pageOutOldest(region, page.getPageId());
            resident = countResident(region);
            if (resident >= cap) {
                return;
            }
        }
        int used = page.usedBytes();
        if (used <= 0) {
            used = properties.getPageBytes();
        }
        byte[] raw = regionPack.readRange(region.id, page.getPageId(), 0, used);
        page.mapFromBytes(raw);
    }

    /**
     * The method unmaps one spilled page that is already mapped.
     * The kept id is skipped.
     * The hot page is skipped.
     * The first remaining spilled page with a segment is unmapped, and the method returns.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#RouxEngine roux-v1.md
     *
     * @param region The region holds the pages.
     * @param keepId The kept id is the page that must stay mapped.
     */
    private void pageOutOldest(Region region, int keepId) {
        for (Page page : region.pages.values()) {
            if (page.getPageId() == keepId) {
                continue;
            }
            if (region.hot != null && page.getPageId() == region.hot.getPageId()) {
                continue;
            }
            if (page.getSegment() != null && page.isSpilled()) {
                page.unmapRam();
                return;
            }
        }
    }

    /**
     * The method counts pages that have a segment.
     *
     * @param region The region holds the pages.
     * @return The count is the number of mapped pages.
     */
    private int countResident(Region region) {
        int n = 0;
        for (Page page : region.pages.values()) {
            if (page.getSegment() != null) {
                n++;
            }
        }
        return n;
    }

    /**
     * The method returns the resident page cap.
     * A stored value below {@code 1} returns {@code 1}.
     *
     * @return The cap is at least one page.
     */
    private int residentPages() {
        int n = properties.getResidentPages();
        if (n < 1) {
            return 1;
        }
        return n;
    }

    /**
     * The method returns the maximum frame size.
     * The cap is {@link #pageBytes} minus {@link PageHeader#SIZE}.
     * A stored value of zero or less returns that cap.
     * A stored value above the cap returns that cap.
     *
     * @return The size is the stored maximum, or the cap.
     */
    private int maxFrameBytes() {
        int n = properties.getMaxFrameBytes();
        int cap = pageBytes() - PageHeader.SIZE;
        if (n <= 0 || n > cap) {
            return cap;
        }
        return n;
    }

    /**
     * The method returns the page size.
     * A stored size below {@link PageHeader#SIZE} plus {@link FrameHeader#SIZE} plus {@code 64} returns {@code 1048576}.
     *
     * @return The size is the stored page size, or {@code 1048576}.
     */
    private int pageBytes() {
        int n = properties.getPageBytes();
        if (n < PageHeader.SIZE + FrameHeader.SIZE + 64) {
            return 1048576;
        }
        return n;
    }

    /**
     * The method returns the open region for an id.
     * The lookup holds the map lock.
     * A missing id returns null.
     *
     * @param regionId The region id is the conversation id.
     * @return The region is the stored state, or null.
     */
    private Region require(String regionId) {
        mapLock.lock();
        try {
            return regions.get(regionId);
        } finally {
            mapLock.unlock();
        }
    }

    /**
     * The method builds a {@link RegionDescriptor}.
     * The spilled byte count is the capacity of every spilled page.
     * The flags come from the hot page header, or {@code 0} when the hot page has no segment.
     * A missing hot page uses id {@code -1}, zero used bytes, and flags {@code 0}.
     * The message count is the index size.
     * The pinned flag is the region pin.
     *
     * @param region The region supplies the descriptor fields.
     * @return The descriptor is the current description.
     */
    private RegionDescriptor describe(Region region) {
        long spilled = 0;
        for (Page page : region.pages.values()) {
            if (page.isSpilled()) {
                spilled += page.getCapacity();
            }
        }
        int flags = 0;
        if (region.hot != null && region.hot.getSegment() != null) {
            flags = PageHeader.flags(region.hot.getSegment());
        }
        return new RegionDescriptor(
                region.id,
                region.hot == null ? -1 : region.hot.getPageId(),
                pageBytes(),
                region.hot == null ? 0 : region.hot.usedBytes(),
                spilled,
                region.index.size(),
                region.regionPinned,
                flags);
    }

    /**
     * The method stores the used-byte counters.
     * The hot total adds {@link Page#usedBytes} for every hot page.
     * The spilled total adds the capacity of every spilled page.
     * The sums are taken while the map lock is held.
     */
    private void recountUsed() {
        long hot = 0;
        long spilled = 0;
        mapLock.lock();
        try {
            for (Region region : regions.values()) {
                if (region.hot != null) {
                    hot += region.hot.usedBytes();
                }
                for (Page page : region.pages.values()) {
                    if (page.isSpilled()) {
                        spilled += page.getCapacity();
                    }
                }
            }
        } finally {
            mapLock.unlock();
        }
        counters.usedBytesHot().set(hot);
        counters.usedBytesSpilled().set(spilled);
    }

    // @aa-sdd PK-1264 docs/specs/java/org/roux/roux.spec.md FINESSE:f-222
    /**
     * The SinkCut type holds one selection from {@link ReichenbachSink}.
     * The ordinals follow the selection order.
     * The headOver flag is true when the pin and the head exceed the allowance.
     * The tokenSum includes the live line when that line is in the selection.
     * A refused selection is a null return from {@link #selectCut}, {@link #selectSlot}, or {@link #selectTape}.
     */
    public static final class SinkCut {

        public final int[] ordinals;
        public final boolean headOver;
        public final int tokenSum;

        /**
         * The constructor stores the selection.
         *
         * @param ordinals The ordinals are the caller indexes in selection order.
         * @param headOver The flag is true when the pin and the head exceed the allowance.
         * @param tokenSum The sum includes every selected piece.
         */
        public SinkCut(int[] ordinals, boolean headOver, int tokenSum) {
            this.ordinals = ordinals;
            this.headOver = headOver;
            this.tokenSum = tokenSum;
        }
    }

    /**
     * The method selects pieces and returns their ordinals.
     * A null seq array, role array, or token array returns null.
     * Arrays of different lengths return null.
     * A missing or shorter head, stay, or live array reads the missing flags as false.
     * Each ordinal is the index in those arrays.
     * A null transcript from {@link ReichenbachSink#select} returns null.
     * The ordinals are copied from the transcript pieces.
     *
     * @param managedTokens The allowance is the token limit passed to the selector.
     * @param seqs The seqs are the message ids, and {@code 0} is the live line.
     * @param roles The roles are the role bytes.
     * @param tokens The tokens are the token counts.
     * @param heads The heads mark the packed head, or null.
     * @param stays The stays mark pieces that join the pin, or null.
     * @param lives The lives mark the live line, or null.
     * @return The cut is the selection, or null when the selection is refused.
     */
    public static SinkCut selectCut(int managedTokens, long[] seqs, byte[] roles, int[] tokens, boolean[] heads,
            boolean[] stays, boolean[] lives) {
        if (seqs == null || roles == null || tokens == null || seqs.length != roles.length
                || seqs.length != tokens.length) {
            return null;
        }
        ReichenbachSink.Piece[] pieces = new ReichenbachSink.Piece[seqs.length];
        for (int i = 0; i < seqs.length; i++) {
            boolean head = heads != null && i < heads.length && heads[i];
            boolean stay = stays != null && i < stays.length && stays[i];
            boolean live = lives != null && i < lives.length && lives[i];
            pieces[i] = new ReichenbachSink.Piece(i, seqs[i], roles[i], tokens[i], head, stay, live);
        }
        ReichenbachSink.Transcript transcript = ReichenbachSink.select(managedTokens, pieces);
        if (transcript == null) {
            return null;
        }
        int[] ordinals = new int[transcript.pieces.length];
        for (int i = 0; i < ordinals.length; i++) {
            ordinals[i] = transcript.pieces[i].ordinal;
        }
        return new SinkCut(ordinals, transcript.headOver, transcript.tokenSum);
    }

    // @aa-sdd aetheris-plan-f-282.001.md
    /**
     * The method selects pieces for a slot and returns their ordinals.
     * The piece arrays are built the same way as {@link #selectCut}.
     * A null seq array, role array, or token array returns null.
     * Arrays of different lengths return null.
     * A missing or shorter head, stay, or live array reads the missing flags as false.
     * Each ordinal is the index in those arrays.
     * A null transcript from {@link ReichenbachSink#selectSlot} returns null.
     * A missing head still selects.
     * A designated head matches {@link #selectCut}.
     *
     * @param allowance The allowance is the token limit passed to the selector.
     * @param seqs The seqs are the message ids, and {@code 0} is the live line.
     * @param roles The roles are the role bytes.
     * @param tokens The tokens are the token counts.
     * @param heads The heads mark the packed head, or null.
     * @param stays The stays mark pieces that join the pin, or null.
     * @param lives The lives mark the live line, or null.
     * @return The cut is the selection, or null when the arrays are refused.
     */
    public static SinkCut selectSlot(int allowance, long[] seqs, byte[] roles, int[] tokens, boolean[] heads,
            boolean[] stays, boolean[] lives) {
        if (seqs == null || roles == null || tokens == null || seqs.length != roles.length
                || seqs.length != tokens.length) {
            return null;
        }
        ReichenbachSink.Piece[] pieces = new ReichenbachSink.Piece[seqs.length];
        for (int i = 0; i < seqs.length; i++) {
            boolean head = heads != null && i < heads.length && heads[i];
            boolean stay = stays != null && i < stays.length && stays[i];
            boolean live = lives != null && i < lives.length && lives[i];
            pieces[i] = new ReichenbachSink.Piece(i, seqs[i], roles[i], tokens[i], head, stay, live);
        }
        ReichenbachSink.Transcript transcript = ReichenbachSink.selectSlot(allowance, pieces);
        if (transcript == null) {
            return null;
        }
        int[] ordinals = new int[transcript.pieces.length];
        for (int i = 0; i < ordinals.length; i++) {
            ordinals[i] = transcript.pieces[i].ordinal;
        }
        return new SinkCut(ordinals, transcript.headOver, transcript.tokenSum);
    }

    /**
     * The method selects the stored messages plus one optional live line.
     * A missing region returns null.
     * An allowance of zero or less returns null.
     * A null or empty head prefix returns null.
     * Only {@link FrameType#MSG} rows are included.
     * A negative live token count adds no live line.
     * Any other live token count adds a piece with message id {@code 0}, role {@link Role#USER}, and the live flag set.
     * That live line is not stored in the region.
     * The stay flags are passed as null.
     * A head flag is true when the message id is in the stored head prefix.
     *
     * @param regionId The region id is the conversation id.
     * @param managedTokens The allowance is the token limit.
     * @param liveTokens The live count is the unsent line, and a negative count means there is no live line.
     * @return The cut is the selection, or null when the selection is refused.
     */
    public SinkCut selectTape(String regionId, int managedTokens, int liveTokens) {
        Region region = require(regionId);
        if (region == null || managedTokens <= 0 || region.headSeqs == null || region.headSeqs.length == 0) {
            return null;
        }
        int size = region.index.size();
        int messages = 0;
        for (long id = 1; id <= size; id++) {
            IndexEntry entry = region.index.get(id);
            if (entry != null && entry.getType() == FrameType.MSG) {
                messages++;
            }
        }
        int extra = liveTokens < 0 ? 0 : 1;
        long[] seqs = new long[messages + extra];
        byte[] roles = new byte[messages + extra];
        int[] tokens = new int[messages + extra];
        boolean[] heads = new boolean[messages + extra];
        boolean[] lives = new boolean[messages + extra];
        int at = 0;
        for (long id = 1; id <= size; id++) {
            IndexEntry entry = region.index.get(id);
            if (entry == null || entry.getType() != FrameType.MSG) {
                continue;
            }
            seqs[at] = id;
            roles[at] = entry.getRole();
            tokens[at] = entry.getTokenCount();
            heads[at] = containsSeq(region.headSeqs, id);
            at++;
        }
        if (extra == 1) {
            seqs[at] = 0L;
            roles[at] = Role.USER;
            tokens[at] = liveTokens;
            lives[at] = true;
        }
        return selectCut(managedTokens, seqs, roles, tokens, heads, null, lives);
    }

    /**
     * The method returns the message id at a stored ordinal.
     * A missing region returns {@code 0}.
     * An ordinal below zero returns {@code 0}.
     * Only {@link FrameType#MSG} rows are counted.
     * Ordinal {@code 0} is the first stored message.
     * An ordinal past the last stored message returns {@code 0}.
     *
     * @param regionId The region id is the conversation id.
     * @param ordinal The ordinal is the zero-based count of stored messages.
     * @return The id is the stored message id, or {@code 0} when the ordinal does not name one.
     */
    public long messageSeqAt(String regionId, int ordinal) {
        Region region = require(regionId);
        if (region == null || ordinal < 0) {
            return 0L;
        }
        int seen = 0;
        int size = region.index.size();
        for (long id = 1; id <= size; id++) {
            IndexEntry entry = region.index.get(id);
            if (entry == null || entry.getType() != FrameType.MSG) {
                continue;
            }
            if (seen == ordinal) {
                return id;
            }
            seen++;
        }
        return 0L;
    }

    /**
     * The method reports whether a head prefix is stored.
     * A missing region returns false.
     * A null prefix or an empty prefix returns false.
     *
     * @param regionId The region id is the conversation id.
     * @return The flag is true when at least one head message id is stored.
     */
    public boolean headDesignated(String regionId) {
        Region region = require(regionId);
        return region != null && region.headSeqs != null && region.headSeqs.length > 0;
    }

    /**
     * The method stores a head count and trims a prefix that no longer matches.
     * A missing region returns without a change.
     * A negative count is stored as {@code 0}.
     * The eligible ids are the first non-system messages up to that count.
     * A stored prefix that already matches the start of those ids is left unchanged.
     * That path does not write the head file.
     * Any other prefix is cut to the matching start and saved.
     * The method does not add ids when the count grows.
     * A shorter eligible run trims the prefix.
     *
     * @param regionId The region id is the conversation id.
     * @param count The count is the requested head length.
     */
    public void applyHeadCount(String regionId, int count) {
        Region region = require(regionId);
        if (region == null) {
            return;
        }
        region.lock.lock();
        try {
            int nextCount = count < 0 ? 0 : count;
            region.headCount = nextCount;
            long[] eligible = eligibleSeqs(region);
            int keep = 0;
            int limit = region.headSeqs.length < eligible.length ? region.headSeqs.length : eligible.length;
            while (keep < limit && region.headSeqs[keep] == eligible[keep]) {
                keep++;
            }
            if (keep == region.headSeqs.length) {
                return;
            }
            long[] trimmed = new long[keep];
            System.arraycopy(region.headSeqs, 0, trimmed, 0, keep);
            region.headSeqs = trimmed;
            saveHead(region);
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method returns the lock mark for one message id.
     * A missing region returns an empty string.
     * An id in the stored head prefix returns {@code  locked}, including the leading space.
     * An id in the eligible run returns {@code  may lock}, including the leading space.
     * Every other id returns an empty string.
     *
     * @param regionId The region id is the conversation id.
     * @param seq The seq is the stored message id.
     * @return The mark is {@code  locked}, {@code  may lock}, or an empty string.
     */
    public String headMark(String regionId, long seq) {
        Region region = require(regionId);
        if (region == null) {
            return "";
        }
        region.lock.lock();
        try {
            if (containsSeq(region.headSeqs, seq)) {
                return " locked";
            }
            if (containsSeq(eligibleSeqs(region), seq)) {
                return " may lock";
            }
            return "";
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method appends one message id to the head prefix.
     * A missing region returns {@code No message seq } plus the id.
     * {@link #prefixMiss} supplies any other refusal.
     * Success appends the id, saves the head prefix, and returns null.
     *
     * @param regionId The region id is the conversation id.
     * @param seq The seq is the message id to lock.
     * @return The text is null when the prefix grew, or one refusal sentence.
     */
    public String lockMessage(String regionId, long seq) {
        Region region = require(regionId);
        if (region == null) {
            return "No message seq " + seq;
        }
        region.lock.lock();
        try {
            String miss = prefixMiss(region, seq, true);
            if (miss != null) {
                return miss;
            }
            long[] next = new long[region.headSeqs.length + 1];
            System.arraycopy(region.headSeqs, 0, next, 0, region.headSeqs.length);
            next[region.headSeqs.length] = seq;
            region.headSeqs = next;
            saveHead(region);
            return null;
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method drops the last id in the head prefix.
     * A missing region returns {@code No message seq } plus the id.
     * {@link #prefixMiss} supplies any other refusal.
     * Success removes that last id, saves the head prefix, and returns null.
     *
     * @param regionId The region id is the conversation id.
     * @param seq The seq is the message id to unlock.
     * @return The text is null when the prefix shrank, or one refusal sentence.
     */
    public String unlockMessage(String regionId, long seq) {
        Region region = require(regionId);
        if (region == null) {
            return "No message seq " + seq;
        }
        region.lock.lock();
        try {
            String miss = prefixMiss(region, seq, false);
            if (miss != null) {
                return miss;
            }
            long[] next = new long[region.headSeqs.length - 1];
            System.arraycopy(region.headSeqs, 0, next, 0, next.length);
            region.headSeqs = next;
            saveHead(region);
            return null;
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method appends a new frame for an existing message id.
     * A null body or an empty body returns {@link OperationStatus#NOT_FOUND} and {@code An empty body is refused.}
     * A missing region, or a row that is missing or is not {@link FrameType#MSG}, returns {@link OperationStatus#NOT_FOUND} and {@code No message seq } plus the id.
     * The token count comes from a new {@link HeuristicTokenCounter}.
     * The payload keeps the stored role, the stored time, and the stored {@link RouxId}.
     * A message that needs more than 32 chunks returns {@link OperationStatus#CORRUPTED_SEGMENT} and {@code more than 32 chunks}.
     * One frame stores chunk word 1.
     * A hot page that cannot hold the frame is rotated first.
     * A failed rotation is returned as that failure.
     * A page that is still too small returns {@link OperationStatus#CORRUPTED_SEGMENT} and {@code hot page too small}.
     *
     * The new frame uses the same message id.
     * The last id on the page changes only when this id is greater than or equal to the current last id.
     * The index row is retargeted to the new page, offset, length, and token count.
     * The identity map is not changed.
     * The prior bytes are not erased.
     * The head prefix is not changed.
     * The appended-frame counter advances by one.
     * Durability {@code append} with an enabled pack publishes the hot page.
     * Success uses an empty message.
     *
     * @param regionId The region id is the conversation id.
     * @param seq The seq is the stored message id.
     * @param text The text is the new body.
     * @return The result is success, or the empty-body, missing, size, or rotation failure.
     */
    // @aa-sdd PK-1466 docs/specs/java/org/roux/roux.spec.md
    public OperationResult editMessage(String regionId, long seq, String text) {
        if (text == null || text.length() == 0) {
            return OperationResult.fail(OperationStatus.NOT_FOUND, "An empty body is refused.");
        }
        Region region = require(regionId);
        if (region == null) {
            return OperationResult.fail(OperationStatus.NOT_FOUND, "No message seq " + seq);
        }
        region.lock.lock();
        try {
            IndexEntry entry = region.index.get(seq);
            if (entry == null || entry.getType() != FrameType.MSG) {
                return OperationResult.fail(OperationStatus.NOT_FOUND, "No message seq " + seq);
            }
            int tokenCount = new HeuristicTokenCounter().count(text);
            byte[] payload = ToonCodec.encode(entry.getRole(), entry.getTimestamp(), tokenCount, text);
            return writeChunks(region, seq, entry.getRole(), entry.getTimestamp(), tokenCount, entry.getRouxId(),
                    payload, false);
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method reads the bytes at a page, an offset, and a length.
     * A missing region returns null.
     * A length below zero returns null.
     * The read uses a synthetic {@link IndexEntry} with message id {@code 0}, role {@code 0}, and a null identity.
     *
     * @param regionId The region id is the conversation id.
     * @param pageId The page id is the slot to read.
     * @param offset The offset is the first byte of the range.
     * @param length The length is the number of bytes to read.
     * @return The bytes are that range, or null when the region or the length is refused.
     */
    byte[] peek(String regionId, int pageId, int offset, int length) {
        Region region = require(regionId);
        if (region == null || length < 0) {
            return null;
        }
        IndexEntry seat = new IndexEntry(0L, pageId, offset, length, 0, 0L, (byte) 0, FrameType.MSG, (short) 0,
                null);
        return readFrameBytes(region, seat);
    }

    /**
     * The method checks a lock or an unlock against the packed prefix.
     * A missing row, or a row that is not {@link FrameType#MSG}, returns {@code No message seq } plus the id.
     * A {@link Role#SYSTEM} row returns {@code A system turn stays in the pin.}
     * An id outside the eligible run returns {@code Seq } plus the id plus {@code  is outside the eligible run.}
     * A lock must name the next open eligible id.
     * Any other lock id returns {@code A lock keeps a packed prefix.}
     * An unlock of an empty prefix, or of an id that is not the last stored id, returns {@code An unlock keeps a packed prefix.}
     * A valid lock or unlock returns null.
     *
     * @param region The region holds the prefix and the index.
     * @param seq The seq is the message id to check.
     * @param lock The flag chooses the lock check when it is true and the unlock check when it is false.
     * @return The text is null when the change is allowed, or one refusal sentence.
     */
    private String prefixMiss(Region region, long seq, boolean lock) {
        IndexEntry entry = region.index.get(seq);
        if (entry == null || entry.getType() != FrameType.MSG) {
            return "No message seq " + seq;
        }
        if (entry.getRole() == Role.SYSTEM) {
            return "A system turn stays in the pin.";
        }
        long[] eligible = eligibleSeqs(region);
        if (!containsSeq(eligible, seq)) {
            return "Seq " + seq + " is outside the eligible run.";
        }
        if (lock) {
            int open = region.headSeqs.length;
            if (open >= eligible.length || eligible[open] != seq) {
                return "A lock keeps a packed prefix.";
            }
            return null;
        }
        if (region.headSeqs.length == 0 || region.headSeqs[region.headSeqs.length - 1] != seq) {
            return "An unlock keeps a packed prefix.";
        }
        return null;
    }

    /**
     * The method lists the message ids that can join the head prefix.
     * A head count of zero or less returns a zero-length array.
     * The list is the first non-system {@link FrameType#MSG} ids, up to the head count.
     * A system row is skipped.
     *
     * @param region The region supplies the head count and the index.
     * @return The ids are the eligible message ids.
     */
    private long[] eligibleSeqs(Region region) {
        int cap = region.headCount;
        if (cap <= 0) {
            return new long[0];
        }
        long[] ids = new long[cap];
        int n = 0;
        int size = region.index.size();
        for (long id = 1; id <= size && n < cap; id++) {
            IndexEntry entry = region.index.get(id);
            if (entry == null || entry.getType() != FrameType.MSG || entry.getRole() == Role.SYSTEM) {
                continue;
            }
            ids[n++] = id;
        }
        long[] out = new long[n];
        System.arraycopy(ids, 0, out, 0, n);
        return out;
    }

    /**
     * The method reports whether an id is in an array.
     * A null array returns false.
     *
     * @param seqs The seqs are the ids to search.
     * @param seq The seq is the id to find.
     * @return The flag is true when the id appears.
     */
    private static boolean containsSeq(long[] seqs, long seq) {
        if (seqs == null) {
            return false;
        }
        for (int i = 0; i < seqs.length; i++) {
            if (seqs[i] == seq) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method loads the head prefix from {@link HeadFile}.
     * The count is cleared to {@code 0} and the ids are cleared to an empty array first.
     * A memory-only process leaves that empty prefix.
     * An id opened through {@link #openRam} leaves that empty prefix.
     * A missing file, a count of zero or less, or an empty id list leaves that empty prefix.
     * Any other file stores its count and its ids.
     *
     * @param region The region receives the prefix.
     */
    private void loadHead(Region region) {
        region.headCount = 0;
        region.headSeqs = new long[0];
        if (region == null || properties.isRamOnly() || isRamRegion(region.id)) {
            return;
        }
        HeadFile file = HeadFile.read(regionPack.packDir(region.id));
        if (file == null || file.count <= 0 || file.seqs == null || file.seqs.length == 0) {
            return;
        }
        region.headCount = file.count;
        region.headSeqs = file.seqs;
    }

    /**
     * The method writes the head prefix.
     * A memory-only process returns without a write.
     * An id opened through {@link #openRam} returns without a write.
     * An {@link IOException} is swallowed, and the in-memory prefix stays.
     *
     * @param region The region supplies the count and the ids.
     */
    private void saveHead(Region region) {
        if (region == null || properties.isRamOnly() || isRamRegion(region.id)) {
            return;
        }
        try {
            HeadFile.write(regionPack.packDir(region.id), region.headCount, region.headSeqs);
        } catch (IOException ignored) {
            // the RAM prefix stays. The next lock writes again.
        }
    }

    /**
     * The method leaves the open region and opens another pack for the same login.
     * The region must already be open.
     * The method does not take the pack lock and it does not release that lock.
     * An empty session name selects the username folder.
     * Any other name selects the username, a tilde, and that name.
     * A name that is not a safe folder name is refused with {@code invalid session name}.
     * A name longer than 32 bytes is refused with that same text.
     * A memory-only engine, a spill that is off, or persist false is refused with {@code named session needs a spill pack}.
     * A half-present target, a bad checksum, or a manifest this engine already refuses is refused, and the live folder stays.
     * The same live folder returns the current kind and count and does not write again.
     * Any other target writes the live pack, closes its pages, and loads the target into this same region id.
     * An absent folder is created empty.
     * A present folder is restored with the indexed count.
     * A load that fails returns to the folder that was live and loads the checkpoint.
     *
     * @param regionId The region id is the username that already holds the lock.
     * @param sessionName The session name is the shelf name, or empty for the username pack.
     * @return The result is new, restored, or refused.
     */
    // @aa-sdd PK-1465 docs/specs/java/org/roux/roux.spec.md
    public OpenResult switchPack(String regionId, String sessionName) {
        if (regionId == null || regionId.length() == 0) {
            return OpenResult.refused("invalid session name");
        }
        Region region = require(regionId);
        if (region == null || region.closed) {
            return OpenResult.refused("region not found");
        }
        if (properties.isRamOnly() || !regionPack.isEnabled() || !properties.sessionPersist()) {
            return OpenResult.refused("named session needs a spill pack");
        }
        String segment = sessionSegment(regionId, sessionName);
        if (segment == null) {
            return OpenResult.refused("invalid session name");
        }
        String refusal = probeSegment(regionId, segment);
        if (refusal != null) {
            return OpenResult.refused(refusal);
        }
        region.lock.lock();
        try {
            if (region.closed) {
                return OpenResult.refused("region not found");
            }
            String live = liveSegment(regionId);
            if (live.equals(segment)) {
                if (region.index.size() == 0) {
                    return OpenResult.neu(region.regionUuid);
                }
                return OpenResult.restored(region.index.size(), region.regionUuid);
            }
            checkpointHot(region);
            writeManifest(region);
            saveHead(region);
            closePages(region);
            regionPack.closeChannel(regionId);
            String previous = live;
            regionPack.bindFolder(regionId, segment);
            OpenResult loaded = openBound(region);
            if (loaded.getKind() == OpenResult.REFUSED) {
                regionPack.bindFolder(regionId, previous);
                openBound(region);
                return loaded;
            }
            return loaded;
        } finally {
            region.lock.unlock();
        }
    }

    /**
     * The method returns the live shelf name.
     * The username folder returns the region id.
     * A named folder returns the text after the tilde.
     * A missing region returns an empty string.
     *
     * @param regionId The region id is the username.
     * @return The name is the live session name.
     */
    // @aa-sdd PK-1465 docs/specs/java/org/roux/roux.spec.md
    public String liveSessionName(String regionId) {
        if (regionId == null || regionId.length() == 0) {
            return "";
        }
        if (require(regionId) == null) {
            return "";
        }
        String segment = liveSegment(regionId);
        int mark = segment.indexOf('~');
        if (mark < 0) {
            return regionId;
        }
        return segment.substring(mark + 1);
    }

    /**
     * The method builds the folder segment for a session name.
     * An empty name returns the region id.
     * A name that is not a safe folder name returns null.
     * A name longer than 32 bytes returns null.
     *
     * @param regionId The region id is the username.
     * @param sessionName The session name is the shelf name, or empty.
     * @return The segment is the folder name, or null when the name is refused.
     */
    private static String sessionSegment(String regionId, String sessionName) {
        if (sessionName == null || sessionName.length() == 0) {
            return regionId;
        }
        if (!RegionOwner.isValid(sessionName)) {
            return null;
        }
        byte[] raw = sessionName.getBytes(StandardCharsets.UTF_8);
        if (raw.length > 32) {
            return null;
        }
        return regionId + "~" + sessionName;
    }

    /**
     * The method returns the live folder name.
     * An unbound region returns the region id.
     *
     * @param regionId The region id is the username.
     * @return The segment is the live folder name.
     */
    private String liveSegment(String regionId) {
        Path dir = regionPack.packDir(regionId);
        Path name = dir.getFileName();
        if (name == null) {
            return regionId;
        }
        return name.toString();
    }

    /**
     * The method checks a target folder before the bind changes.
     * An absent folder returns null.
     * A manifest without a pack file returns the incomplete-pack sentence.
     * A bad checksum returns that same sentence.
     * A manifest this engine already refuses returns that refusal.
     *
     * @param regionId The region id is the username.
     * @param segment The segment is the target folder name.
     * @return The text is the refusal, or null when the target may be opened.
     */
    private String probeSegment(String regionId, String segment) {
        Path home = regionPack.regionDir(regionId);
        Path root = home.getParent();
        if (root == null) {
            return "named session needs a spill pack";
        }
        Path dir = root.resolve(segment);
        boolean pack = Files.isRegularFile(dir.resolve("pages.bin"));
        boolean manFile = Files.isRegularFile(dir.resolve(RegionManifest.FILE_NAME));
        if (!pack && !manFile) {
            return null;
        }
        if (!pack && manFile) {
            return incomplete(dir);
        }
        if (manFile) {
            RegionManifest man;
            try {
                man = RegionManifest.read(dir);
            } catch (IOException ex) {
                return incomplete(dir);
            }
            if (man == null || man.getFormatVersion() != PageHeader.VERSION) {
                return incomplete(dir);
            }
            if (man.getPageBytes() != pageBytes()) {
                return "roux pack page-bytes mismatch (manifest " + man.getPageBytes()
                        + ", yaml " + pageBytes() + ")";
            }
            if (man.getOwner() != null && man.getOwner().length() > 0 && !man.getOwner().equals(regionId)) {
                return incomplete(dir);
            }
        }
        if (!slotsAgree(dir)) {
            return incomplete(dir);
        }
        return null;
    }

    /**
     * The method reports whether each stored page agrees with its checksum.
     * A missing pack file returns true.
     * A header that ends the file returns true.
     * A bad version, a bad page id, or a bad checksum returns false.
     *
     * @param dir The directory is the target pack folder.
     * @return The flag is true when the pack can be opened.
     */
    private boolean slotsAgree(Path dir) {
        Path file = dir.resolve("pages.bin");
        if (!Files.isRegularFile(file)) {
            return true;
        }
        FileChannel channel = null;
        try {
            long size = Files.size(file);
            long pitch = pageBytes();
            channel = FileChannel.open(file, StandardOpenOption.READ);
            int pageId = 0;
            while (pageId >= 0) {
                long at = (long) pageId * pitch;
                if (at < 0) {
                    return false;
                }
                if (size < at + PageHeader.SIZE) {
                    return true;
                }
                byte[] header = readAt(channel, at, PageHeader.SIZE);
                if (header[0] != (byte) 'R' || header[1] != (byte) 'O' || header[2] != (byte) 'U'
                        || header[3] != (byte) 'X') {
                    return true;
                }
                int version = (header[4] & 0xFF) | ((header[5] & 0xFF) << 8);
                int id = leInt(header, PageHeader.OFF_PAGE_ID);
                if (version != PageHeader.VERSION || id != pageId) {
                    return false;
                }
                int used = leInt(header, PageHeader.OFF_USED);
                if (used < PageHeader.SIZE || used > pageBytes()) {
                    return false;
                }
                byte[] body = readAt(channel, at, used);
                if (!crcMatches(body, used)) {
                    return false;
                }
                pageId++;
            }
            return false;
        } catch (IOException ex) {
            return false;
        } finally {
            if (channel != null) {
                try {
                    channel.close();
                } catch (IOException ignored) {
                    // the probe is finished
                }
            }
        }
    }

    /**
     * The method copies bytes from an absolute file position.
     * A short read leaves the unread tail as zeros.
     *
     * @param channel The channel is the open pack file.
     * @param pos The position is the first byte to copy.
     * @param length The length is the number of bytes to copy.
     * @return The array is the heap copy.
     * @throws IOException The read failed.
     */
    private static byte[] readAt(FileChannel channel, long pos, int length) throws IOException {
        byte[] dest = new byte[length];
        ByteBuffer buf = ByteBuffer.wrap(dest);
        while (buf.hasRemaining()) {
            int at = buf.position();
            int n = channel.read(buf, pos + at);
            if (n < 0) {
                break;
            }
        }
        return dest;
    }

    /**
     * The method reports whether the page checksum matches the used bytes.
     * The checksum skips its own four bytes.
     *
     * @param page The page is the used prefix of one slot.
     * @param used The used count is the last byte covered by the checksum.
     * @return The flag is true when the stored checksum matches.
     */
    private static boolean crcMatches(byte[] page, int used) {
        if (page == null || used < PageHeader.OFF_CRC + 4 || used > page.length) {
            return false;
        }
        CRC32C crc = new CRC32C();
        crc.update(page, 0, PageHeader.OFF_CRC);
        int rest = used - (PageHeader.OFF_CRC + 4);
        if (rest > 0) {
            crc.update(page, PageHeader.OFF_CRC + 4, rest);
        }
        int stored = leInt(page, PageHeader.OFF_CRC);
        return stored == (int) crc.getValue();
    }

    /**
     * The method reads a little-endian integer.
     *
     * @param raw The raw bytes hold the integer.
     * @param off The offset is the first byte.
     * @return The value is the integer.
     */
    private static int leInt(byte[] raw, int off) {
        return (raw[off] & 0xFF)
                | ((raw[off + 1] & 0xFF) << 8)
                | ((raw[off + 2] & 0xFF) << 16)
                | ((raw[off + 3] & 0xFF) << 24);
    }

    /**
     * The method builds the incomplete-pack sentence for one directory.
     *
     * @param dir The directory is the pack folder named in the sentence.
     * @return The text is the refusal.
     */
    private static String incomplete(Path dir) {
        return "roux pack incomplete or corrupt; delete " + dir + " or revert yaml";
    }

    /**
     * The method opens the folder that is already bound.
     * An absent folder is created empty and written as a new pack.
     * A present folder is loaded into the same region.
     * The pack lock stays as it is.
     *
     * @param region The region receives the loaded pages.
     * @return The result is new, restored, or refused.
     */
    private OpenResult openBound(Region region) {
        boolean pack = regionPack.packExists(region.id);
        boolean manFile = regionPack.manifestExists(region.id);
        if (!pack && !manFile) {
            Path dir = regionPack.packDir(region.id);
            try {
                Files.createDirectories(dir);
            } catch (IOException ex) {
                return OpenResult.refused(incomplete(dir));
            }
            blankRegion(region);
            checkpointHot(region);
            writeManifest(region);
            saveHead(region);
            recountUsed();
            loadClip(region);
            return OpenResult.neu(region.regionUuid);
        }
        return fillBound(region);
    }

    /**
     * The method clears the live pages and starts an empty region.
     * The region id and the region lock stay.
     * The region receives a new identifier.
     *
     * @param region The region is reset in place.
     */
    private void blankRegion(Region region) {
        closePages(region);
        region.index.reset();
        region.byId.clear();
        region.hot = new Page(0, pageBytes());
        region.pages.put(Integer.valueOf(0), region.hot);
        region.nextMessageId = 1;
        region.nextPageId = 1;
        region.headCount = 0;
        region.headSeqs = new long[0];
        region.regionUuid = UUID.randomUUID();
        region.createdEpochMs = System.currentTimeMillis();
        region.regionPinned = false;
        region.restored = false;
        region.clip = null;
        counters.pagesAllocated().incrementAndGet();
    }

    /**
     * The method loads the bound pack into the region that is already open.
     * The region stays in the map.
     * The pack lock stays held.
     * A failure leaves the pages unloaded and does not close the index.
     *
     * @param region The region receives the stored pages.
     * @return The result is restored, or refused.
     */
    private OpenResult fillBound(Region region) {
        Path dir = regionPack.packDir(region.id);
        String refuse = incomplete(dir);
        RegionManifest man = null;
        if (regionPack.manifestExists(region.id)) {
            try {
                man = RegionManifest.read(dir);
            } catch (IOException ex) {
                return OpenResult.refused(refuse);
            }
            if (man == null) {
                return OpenResult.refused(refuse);
            }
        }
        int taken;
        try {
            taken = walkSlots(region.id);
        } catch (IOException ex) {
            return OpenResult.refused(refuse);
        }
        int hotId;
        boolean repair;
        if (taken < 0) {
            return OpenResult.refused(refuse);
        }
        if (man == null) {
            if (taken < 1) {
                return OpenResult.refused(refuse);
            }
            hotId = taken - 1;
            repair = true;
        } else if (man.getPageCount() < 1) {
            return OpenResult.refused(refuse);
        } else if (taken < man.getPageCount()) {
            return OpenResult.refused(refuse);
        } else if (taken > man.getPageCount()) {
            hotId = taken - 1;
            repair = true;
        } else {
            hotId = man.getHotPageId();
            if (man.getPageCount() < 1 || hotId < 0 || hotId >= man.getPageCount()) {
                return OpenResult.refused(refuse);
            }
            repair = false;
        }
        closePages(region);
        region.index.reset();
        region.byId.clear();
        region.nextMessageId = 1;
        region.nextPageId = taken;
        if (man != null) {
            region.regionUuid = man.getRegionUuid();
            region.createdEpochMs = man.getCreatedEpochMs();
        } else {
            region.regionUuid = UUID.randomUUID();
            region.createdEpochMs = System.currentTimeMillis();
        }
        Path pack = regionPack.packFile(region.id);
        try {
            for (int pageId = 0; pageId < taken; pageId++) {
                byte[] header = regionPack.readRange(region.id, pageId, 0, PageHeader.SIZE);
                Arena headerArena = Arena.ofConfined();
                int used;
                boolean magic;
                try {
                    MemorySegment hs = headerArena.allocate(PageHeader.SIZE, 8);
                    MemorySegment.copy(header, 0, hs, ValueLayout.JAVA_BYTE, 0, header.length);
                    used = PageHeader.usedBytes(hs);
                    magic = PageHeader.hasMagic(hs);
                } finally {
                    headerArena.close();
                }
                if (used < PageHeader.SIZE) {
                    used = PageHeader.SIZE;
                }
                if (used > pageBytes()) {
                    used = pageBytes();
                }
                byte[] slot = regionPack.readRange(region.id, pageId, 0, used);
                if (pageId == hotId) {
                    Page hot = new Page(pageId, pageBytes());
                    hot.mapFromBytes(slot);
                    hot.adoptAsHot();
                    hot.setFile(pack);
                    region.hot = hot;
                    region.pages.put(Integer.valueOf(pageId), hot);
                    if (magic) {
                        scanFrames(region, hot.getSegment(), pageId, used);
                    }
                } else {
                    Page spilled = Page.spilled(pageId, pageBytes(), pack);
                    region.pages.put(Integer.valueOf(pageId), spilled);
                    if (magic) {
                        Arena slotArena = Arena.ofConfined();
                        try {
                            MemorySegment seg = slotArena.allocate(used < 8 ? 8 : used, 8);
                            MemorySegment.copy(slot, 0, seg, ValueLayout.JAVA_BYTE, 0, slot.length);
                            scanFrames(region, seg, pageId, used);
                        } finally {
                            slotArena.close();
                        }
                    }
                }
            }
        } catch (IOException ex) {
            return OpenResult.refused(refuse);
        }
        if (region.hot == null) {
            return OpenResult.refused(refuse);
        }
        region.nextMessageId = region.index.size() + 1;
        counters.pagesAllocated().addAndGet(region.pages.size());
        recountUsed();
        if (repair) {
            writeManifest(region);
        }
        loadHead(region);
        region.restored = true;
        noteOpened(region.id);
        loadClip(region);
        return OpenResult.restored(region.index.size(), region.regionUuid);
    }

    /**
     * The method stores the prune bean.
     * A null bean leaves opens without a sweep.
     *
     * @param bean The bean is the sweep owner.
     */
    void attachPrune(DeadPagePrune bean) {
        this.prune = bean;
    }

    /**
     * The method copies the open region ids.
     *
     * @return The ids are the regions in the map.
     */
    List<String> openRegionIds() {
        ArrayList<String> ids = new ArrayList<String>();
        mapLock.lock();
        try {
            for (String id : regions.keySet()) {
                ids.add(id);
            }
        } finally {
            mapLock.unlock();
        }
        return ids;
    }

    /**
     * The method pins one page.
     * A missing region returns without a change.
     *
     * @param regionId The region id is the conversation id.
     * @param pageId The page id is the page to pin.
     */
    void pinPage(String regionId, int pageId) {
        Region region = require(regionId);
        if (region == null) {
            return;
        }
        region.pins.pin(pageId);
    }

    /**
     * The method drops one pin.
     * A missing region returns without a change.
     *
     * @param regionId The region id is the conversation id.
     * @param pageId The page id is the page to unpin.
     */
    void unpinPage(String regionId, int pageId) {
        Region region = require(regionId);
        if (region == null) {
            return;
        }
        region.pins.unpin(pageId);
    }

    /**
     * The method releases dead slots in one region.
     * A region with no pack returns {@code 0}.
     * A restored region returns {@code 0}.
     * The hot page stays.
     * A pinned page stays.
     * A slot without the page magic stays.
     * A page inside a stored span stays.
     * A hot page that is ahead of the last force releases nothing.
     * A pass that releases nothing stays quiet.
     *
     * @param regionId The region id is the conversation id.
     * @return The count is the number of slots released.
     */
    // @aa-sdd PK-1469 docs/specs/java/org/roux/roux.spec.md
    int sweepDead(String regionId) {
        if (regionId == null || regionId.length() == 0) {
            return 0;
        }
        if (!regionPack.isEnabled() || properties.isRamOnly() || isRamRegion(regionId)) {
            return 0;
        }
        if (!regionPack.packExists(regionId)) {
            return 0;
        }
        Region region = require(regionId);
        if (region == null || region.restored) {
            return 0;
        }
        int[] dead;
        region.lock.lock();
        try {
            dead = copyDead(region);
        } finally {
            region.lock.unlock();
        }
        int released = 0;
        for (int i = 0; i < dead.length; i++) {
            if (prune != null && prune.stopRequested()) {
                break;
            }
            int pageId = dead[i];
            region.lock.lock();
            try {
                if (!isDead(region, pageId)) {
                    continue;
                }
                if (!hotForced(region)) {
                    return released;
                }
                boolean tail;
                try {
                    tail = slotIsTail(region, pageId);
                    regionPack.releaseSlot(region.id, pageId, tail);
                } catch (IOException ex) {
                    return released;
                }
                if (tail) {
                    try {
                        long size = Files.size(regionPack.packFile(region.id));
                        region.nextPageId = (int) (size / (long) pageBytes());
                    } catch (IOException ex) {
                        region.nextPageId = pageId;
                    }
                    dropFrom(region, region.nextPageId);
                    writeManifest(region);
                } else {
                    dropOne(region, pageId);
                }
                released++;
                counters.pagesReleased().incrementAndGet();
            } finally {
                region.lock.unlock();
            }
        }
        return released;
    }

    /**
     * The method asks the bean to sweep one region.
     * A missing bean returns without a sweep.
     *
     * @param regionId The region id is the conversation id.
     */
    private void noteOpened(String regionId) {
        DeadPagePrune bean = this.prune;
        if (bean == null) {
            return;
        }
        bean.sweepOne(regionId);
    }

    /**
     * The method copies the dead page ids from the highest id downward.
     *
     * @param region The region holds the index and the pages.
     * @return The ids are the dead slots.
     */
    private int[] copyDead(Region region) {
        int count = 0;
        for (int pageId = region.nextPageId - 1; pageId >= 0; pageId--) {
            if (isDead(region, pageId)) {
                count++;
            }
        }
        int[] dead = new int[count];
        int at = 0;
        for (int pageId = region.nextPageId - 1; pageId >= 0; pageId--) {
            if (isDead(region, pageId)) {
                dead[at] = pageId;
                at++;
            }
        }
        return dead;
    }

    /**
     * The method reports whether one issued page is dead.
     * The hot page is not dead.
     * A pin count above zero is not dead.
     * A page inside a stored span is not dead.
     * A slot without the page magic is not dead.
     *
     * @param region The region holds the index and the pages.
     * @param pageId The page id is the slot to test.
     * @return The flag is true when the slot may be released.
     */
    private boolean isDead(Region region, int pageId) {
        if (pageId < 0 || pageId >= region.nextPageId) {
            return false;
        }
        if (region.hot != null && region.hot.getPageId() == pageId) {
            return false;
        }
        if (region.pins.count(pageId) > 0) {
            return false;
        }
        if (spanKeeps(region, pageId)) {
            return false;
        }
        return slotHasMagic(region, pageId);
    }

    /**
     * The method reports whether a stored span covers one page.
     * A stored last page of {@code 0} means the page the row already names.
     *
     * @param region The region holds the index.
     * @param pageId The page id is the slot to test.
     * @return The flag is true when a live row keeps the page.
     */
    private boolean spanKeeps(Region region, int pageId) {
        int size = region.index.size();
        for (long seq = 1; seq <= size; seq++) {
            IndexEntry entry = region.index.get(seq);
            if (entry == null) {
                continue;
            }
            int start = entry.getPageId();
            int last = region.index.lastPage(seq);
            int end = last == 0 ? start : last;
            if (end < start) {
                int swap = start;
                start = end;
                end = swap;
            }
            if (pageId >= start && pageId <= end) {
                return true;
            }
        }
        return false;
    }

    /**
     * The method checks the page magic.
     * A mapped page uses its segment.
     * Any other page uses the slot header.
     *
     * @param region The region holds the pages.
     * @param pageId The page id is the slot to read.
     * @return The flag is true when the first four bytes are the page magic.
     */
    private boolean slotHasMagic(Region region, int pageId) {
        Page page = region.pages.get(Integer.valueOf(pageId));
        if (page != null && page.getSegment() != null) {
            return PageHeader.hasMagic(page.getSegment());
        }
        try {
            byte[] header = regionPack.readRange(region.id, pageId, 0, PageHeader.SIZE);
            Arena arena = Arena.ofConfined();
            try {
                MemorySegment seg = arena.allocate(PageHeader.SIZE, 8);
                int n = header.length;
                if (n > PageHeader.SIZE) {
                    n = PageHeader.SIZE;
                }
                MemorySegment.copy(header, 0, seg, ValueLayout.JAVA_BYTE, 0, n);
                return PageHeader.hasMagic(seg);
            } finally {
                arena.close();
            }
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * The method compares the hot page with its slot.
     * A missing segment, a missing file, a short slot, or any different byte returns false.
     *
     * @param region The region holds the hot page.
     * @return The flag is true when the used bytes match the slot.
     */
    private boolean hotForced(Region region) {
        if (region.hot == null || region.hot.getSegment() == null) {
            return false;
        }
        int used = region.hot.usedBytes();
        if (used < PageHeader.SIZE) {
            return false;
        }
        try {
            if (!regionPack.packExists(region.id)) {
                return false;
            }
            long size = Files.size(regionPack.packFile(region.id));
            long off = regionPack.slotOffset(region.hot.getPageId());
            if (size < off + used) {
                return false;
            }
            byte[] slot = regionPack.readRange(region.id, region.hot.getPageId(), 0, used);
            MemorySegment seg = region.hot.getSegment();
            for (int i = 0; i < used; i++) {
                if (seg.get(ValueLayout.JAVA_BYTE, i) != slot[i]) {
                    return false;
                }
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * The method reports whether a slot is the current tail.
     * The slot is a tail when no later page header fits in the file.
     *
     * @param region The region names the pack.
     * @param pageId The page id is the slot to test.
     * @return The flag is true when the file ends at or before the slot end.
     * @throws IOException The pack file could not be sized.
     */
    private boolean slotIsTail(Region region, int pageId) throws IOException {
        long size = Files.size(regionPack.packFile(region.id));
        long end = regionPack.slotOffset(pageId) + pageBytes();
        return size <= end;
    }

    /**
     * The method closes pages whose ids are at or above a bound.
     * The hot page stays.
     *
     * @param region The region holds the pages.
     * @param first The first id is the lowest id to drop.
     */
    private void dropFrom(Region region, int first) {
        ArrayList<Integer> gone = new ArrayList<Integer>();
        for (Integer id : region.pages.keySet()) {
            if (id.intValue() >= first && (region.hot == null || id.intValue() != region.hot.getPageId())) {
                gone.add(id);
            }
        }
        for (int i = 0; i < gone.size(); i++) {
            dropOne(region, gone.get(i).intValue());
        }
    }

    /**
     * The method closes one page and removes it from the map.
     * The hot page stays.
     *
     * @param region The region holds the pages.
     * @param pageId The page id is the page to drop.
     */
    private void dropOne(Region region, int pageId) {
        if (region.hot != null && region.hot.getPageId() == pageId) {
            return;
        }
        Page page = region.pages.remove(Integer.valueOf(pageId));
        if (page != null) {
            page.close();
        }
    }

    // @aa-sdd PK-1265 docs/specs/java/org/roux/roux.spec.md FINESSE:f-222
    /**
     * The Region type holds one conversation.
     * The id is the conversation id.
     * The lock is the region lock.
     * The pin table tracks pinned pages.
     * The index is the off-heap message index.
     * The page map stores each page by page id.
     * The identity map stores a {@link RouxId} and its message id.
     * The hot page receives writes.
     * The next message id starts at {@code 1}.
     * The next page id starts at {@code 1}.
     * The region pin starts false.
     * The head count starts at {@code 0}, and the head ids start empty.
     * The operator lock starts unset.
     * The region UUID is random, and the creation time is the current epoch milliseconds.
     * The closed flag starts false.
     */
    /**
     * The method returns the live head origin.
     * An absent clip returns {@code 0}.
     *
     * @param regionId The region id is the conversation id.
     * @return The origin is the floor, or {@code 0}.
     */
    public long clipOrigin(String regionId) {
        Region region = require(regionId);
        if (region == null || region.clip == null || !region.clip.isLive()) {
            return 0L;
        }
        return region.clip.getOrigin();
    }

    /**
     * The method returns one status line for the clip.
     * An absent clip returns {@code head: 0 auto 0 0 0}.
     *
     * @param regionId The region id is the conversation id.
     * @return The line is the head origin, the mode, the capacity, the estimate, and the remaining count.
     */
    public String headLine(String regionId) {
        Region region = require(regionId);
        if (region == null || region.clip == null || !region.clip.isLive()) {
            return "head: 0 auto 0 0 0";
        }
        String mode = region.clip.isRewind() ? "rewind" : "auto";
        return "head: " + region.clip.getOrigin() + " " + mode + " " + region.clip.getCapacity() + " "
                + region.clip.getEstimate() + " " + region.clip.getRemaining();
    }

    /**
     * The method sums the token count of the current head.
     * A stored count above zero is used as it is.
     * A missing count uses {@link HeuristicTokenCounter#count}.
     * The extra count is added after the stored messages.
     *
     * @param regionId The region id is the conversation id.
     * @param maxNonSystem The cap is the non-system message limit.
     * @param maxTokens The budget is the token limit.
     * @param extraTokens The extra count is the live line and any mood line that is not stored.
     * @return The estimate is the token count of that head.
     */
    public int clipEstimate(String regionId, int maxNonSystem, int maxTokens, int extraTokens) {
        Region region = require(regionId);
        int extra = extraTokens > 0 ? extraTokens : 0;
        if (region == null) {
            return extra;
        }
        long floor = clipOrigin(regionId);
        long[] ids;
        if (floor > 0L) {
            ids = windowIds(regionId, maxNonSystem, true, maxTokens, floor);
        } else {
            ids = windowIds(regionId, maxNonSystem, true, maxTokens);
        }
        int estimate = extra;
        for (int i = 0; i < ids.length; i++) {
            estimate += storedTokens(region, ids[i]);
        }
        return estimate;
    }

    /**
     * The method rolls the head until the remaining count is above one percent.
     * The pre-roll ids are not the origin this method stores.
     * A roll sets bit 0 and clears bit 1.
     * A roll origin does not move backward.
     * The live line is the extra token count and is not dropped.
     * An atom is dropped whole.
     * A hot page whose last message id is behind the origin is published when it holds no leading system frame and no mood frame.
     *
     * @param regionId The region id is the conversation id.
     * @param maxNonSystem The cap is the non-system message limit.
     * @param maxTokens The budget is the token limit.
     * @param capacity The capacity is the token window, or {@code 0}.
     * @param extraTokens The extra count is the live line and any mood line that is not stored.
     * @param moodMark The mark is text that keeps a mood atom, or null.
     */
    // @aa-sdd PK-1470 docs/specs/java/org/roux/roux.spec.md
    public void rollHead(String regionId, int maxNonSystem, int maxTokens, int capacity, int extraTokens,
            String moodMark) {
        Region region = require(regionId);
        if (region == null) {
            return;
        }
        int extra = extraTokens > 0 ? extraTokens : 0;
        int cap = capacity > 0 ? capacity : 0;
        region.lock.lock();
        try {
            long previous = 0L;
            boolean previousAuto = false;
            boolean previousRewind = false;
            if (region.clip != null && region.clip.isLive()) {
                previous = region.clip.getOrigin();
                previousAuto = !region.clip.isRewind();
                previousRewind = region.clip.isRewind();
            }
            long[] ids;
            if (previous > 0L) {
                ids = windowIds(regionId, maxNonSystem, true, maxTokens, previous);
            } else {
                ids = windowIds(regionId, maxNonSystem, true, maxTokens);
            }
            long[] pinned = pinnedSystemIds(region);
            ArrayList<long[]> atoms = tailAtoms(region, ids, pinned);
            boolean dropped = false;
            while (true) {
                int estimate = tokenIds(region, pinned) + tokenAtoms(region, atoms) + extra;
                int remaining = 0;
                if (cap > 0 && estimate < cap) {
                    remaining = cap - estimate;
                }
                boolean above = cap > 0 && (long) remaining * 100L > (long) cap;
                if (above) {
                    break;
                }
                if (atoms.isEmpty()) {
                    break;
                }
                if (moodAtom(region, atoms.get(0), moodMark)) {
                    break;
                }
                if (atoms.size() == 1 && extra == 0) {
                    break;
                }
                atoms.remove(0);
                dropped = true;
            }
            if (!dropped && previousRewind) {
                return;
            }
            long origin;
            if (!dropped) {
                origin = previousAuto ? previous : 0L;
            } else if (atoms.isEmpty()) {
                origin = region.nextMessageId;
            } else {
                origin = atoms.get(0)[0];
            }
            if (previousAuto && origin < previous) {
                origin = previous;
            }
            long[] after;
            if (origin > 0L) {
                after = windowIds(regionId, maxNonSystem, true, maxTokens, origin);
            } else {
                after = windowIds(regionId, maxNonSystem, true, maxTokens);
            }
            int estimate = extra;
            for (int i = 0; i < after.length; i++) {
                estimate += storedTokens(region, after[i]);
            }
            int remaining = 0;
            if (cap > 0 && estimate < cap) {
                remaining = cap - estimate;
            }
            region.clip = ClipRecord.auto(origin, cap, estimate, remaining, System.currentTimeMillis());
            publishReel(region, origin, moodMark);
            writeClip(region);
        } finally {
            if (region.lock.isHeldByCurrentThread()) {
                region.lock.unlock();
            }
        }
    }

    /**
     * The method sets or clears a rewind.
     * A bare rewind stores origin {@code 0} and clears bit 1.
     * A sequence rewind sets bit 1.
     * A missing sequence returns {@code No message seq } plus the number.
     * A sequence in the leading system run returns {@code seq is in the leading system run}.
     *
     * @param regionId The region id is the conversation id.
     * @param bare The flag is true when the command named no sequence.
     * @param seq The sequence is the requested origin when {@code bare} is false.
     * @return The text is null when the origin was stored, or the refusal sentence.
     */
    public String rewind(String regionId, boolean bare, long seq) {
        Region region = require(regionId);
        if (region == null) {
            if (bare) {
                return null;
            }
            return "No message seq " + seq;
        }
        region.lock.lock();
        try {
            long origin = 0L;
            boolean rewind = false;
            if (!bare) {
                IndexEntry entry = region.index.get(seq);
                if (entry == null || entry.getType() != FrameType.MSG) {
                    return "No message seq " + seq;
                }
                if (isPinned(leadingRun(region), seq)) {
                    return "seq is in the leading system run";
                }
                origin = seq;
                rewind = true;
            }
            int cap = 0;
            if (region.clip != null) {
                cap = region.clip.getCapacity();
            }
            long[] ids;
            if (origin > 0L) {
                ids = windowIds(regionId, 0, true, 0, origin);
            } else {
                ids = windowIds(regionId, 0, true, 0);
            }
            int estimate = 0;
            for (int i = 0; i < ids.length; i++) {
                estimate += storedTokens(region, ids[i]);
            }
            int remaining = 0;
            if (cap > 0 && estimate < cap) {
                remaining = cap - estimate;
            }
            if (rewind) {
                region.clip = ClipRecord.rewind(origin, cap, estimate, remaining, System.currentTimeMillis());
            } else {
                region.clip = ClipRecord.auto(origin, cap, estimate, remaining, System.currentTimeMillis());
            }
            writeClip(region);
            return null;
        } finally {
            if (region.lock.isHeldByCurrentThread()) {
                region.lock.unlock();
            }
        }
    }

    /**
     * The method clears a rewind after a successful complete.
     * Bit 1 clear leaves a roll origin where it is.
     * Bit 1 set stores origin {@code 0} and clears bit 1.
     *
     * @param regionId The region id is the conversation id.
     */
    public void noteClipSuccess(String regionId) {
        Region region = require(regionId);
        if (region == null || region.clip == null || !region.clip.isRewind()) {
            return;
        }
        region.lock.lock();
        try {
            if (region.clip == null || !region.clip.isRewind()) {
                return;
            }
            int cap = region.clip.getCapacity();
            long[] ids = windowIds(regionId, 0, true, 0);
            int estimate = 0;
            for (int i = 0; i < ids.length; i++) {
                estimate += storedTokens(region, ids[i]);
            }
            int remaining = 0;
            if (cap > 0 && estimate < cap) {
                remaining = cap - estimate;
            }
            region.clip = ClipRecord.auto(0L, cap, estimate, remaining, System.currentTimeMillis());
            writeClip(region);
        } finally {
            if (region.lock.isHeldByCurrentThread()) {
                region.lock.unlock();
            }
        }
    }

    /**
     * The method loads {@code clip.bin} into the open region.
     * A RAM-only region is left unchanged.
     * An absent file or a checksum miss leaves the clip unset.
     *
     * @param region The region receives the record.
     */
    private void loadClip(Region region) {
        if (region == null) {
            return;
        }
        if (!properties.isDiskBacked() || isRamRegion(region.id)) {
            return;
        }
        region.clip = ClipRecord.read(regionPack.packDir(region.id));
    }

    /**
     * The method writes the live clip beside the manifest.
     * Spill off writes no file.
     * A RAM-only region writes no file.
     *
     * @param region The region holds the record.
     */
    private void writeClip(Region region) {
        if (region == null || region.clip == null) {
            return;
        }
        if (!properties.isDiskBacked() || isRamRegion(region.id)) {
            return;
        }
        try {
            ClipRecord.write(regionPack.packDir(region.id), region.clip);
        } catch (IOException ignored) {
            // the in-memory record still clips the next prompt
        }
    }

    /**
     * The method publishes a hot page that sits entirely behind the origin.
     * A page that holds a leading system frame stays.
     * A page that holds a mood frame stays.
     * A page that is already unmapped stays.
     * {@link RegionPack#publishPage} is called and is not changed.
     *
     * @param region The region holds the pages.
     * @param origin The origin is the head sequence.
     * @param moodMark The mark is text that keeps a page, or null.
     */
    private void publishReel(Region region, long origin, String moodMark) {
        if (origin <= 0L || region == null) {
            return;
        }
        if (!properties.isDiskBacked() || !regionPack.isEnabled() || isRamRegion(region.id)) {
            return;
        }
        long[] leading = leadingRun(region);
        ArrayList<Page> copy = new ArrayList<Page>();
        java.util.Iterator<Page> it = region.pages.values().iterator();
        while (it.hasNext()) {
            copy.add(it.next());
        }
        for (int p = 0; p < copy.size(); p++) {
            Page page = copy.get(p);
            if (page.getSegment() == null || !"HOT".equals(page.residency())) {
                continue;
            }
            long last = 0L;
            boolean blocked = false;
            boolean any = false;
            long size = region.index.size();
            for (long id = 1; id <= size; id++) {
                IndexEntry entry = region.index.get(id);
                if (entry == null || entry.getPageId() != page.getPageId() || entry.getType() != FrameType.MSG) {
                    continue;
                }
                any = true;
                if (id > last) {
                    last = id;
                }
                if (isPinned(leading, id)) {
                    blocked = true;
                }
                if (moodMark != null && moodMark.length() > 0) {
                    DecodedFrame frame = readMessage(region.id, id);
                    if (frame != null && frame.getText() != null && frame.getText().indexOf(moodMark) >= 0) {
                        blocked = true;
                    }
                }
            }
            if (!any || blocked || last >= origin) {
                continue;
            }
            try {
                regionPack.publishPage(region.id, page);
            } catch (IOException ignored) {
                // a page that cannot be published stays hot
            }
        }
    }

    /**
     * The method returns the opening system run.
     * The run stops at the first row that is not a system message.
     *
     * @param region The region supplies the index.
     * @return The ids are the leading system run.
     */
    private static long[] leadingRun(Region region) {
        int size = region.index.size();
        ArrayList<Long> leading = new ArrayList<Long>();
        for (long id = 1; id <= size; id++) {
            IndexEntry entry = region.index.get(id);
            if (entry == null || entry.getType() != FrameType.MSG || entry.getRole() != Role.SYSTEM) {
                break;
            }
            leading.add(Long.valueOf(id));
        }
        long[] ids = new long[leading.size()];
        for (int i = 0; i < leading.size(); i++) {
            ids[i] = leading.get(i).longValue();
        }
        return ids;
    }

    /**
     * The method groups the tail of a window into atoms.
     * A user, tool, or tool-call row keeps the following assistant row when the ids are consecutive.
     *
     * @param region The region supplies the roles.
     * @param ids The ids are the current window.
     * @param pinned The pinned ids stay out of the tail.
     * @return The atoms are oldest first.
     */
    private static ArrayList<long[]> tailAtoms(Region region, long[] ids, long[] pinned) {
        ArrayList<long[]> atoms = new ArrayList<long[]>();
        int i = 0;
        while (i < ids.length) {
            long id = ids[i];
            if (isPinned(pinned, id)) {
                i++;
                continue;
            }
            if (i + 1 < ids.length) {
                long next = ids[i + 1];
                IndexEntry left = region.index.get(id);
                IndexEntry right = region.index.get(next);
                if (left != null && right != null && next == id + 1L && right.getRole() == Role.ASSISTANT
                        && (left.getRole() == Role.USER || left.getRole() == Role.TOOL
                                || left.getRole() == Role.TOOL_CALL)
                        && !isPinned(pinned, next)) {
                    long[] pair = new long[2];
                    pair[0] = id;
                    pair[1] = next;
                    atoms.add(pair);
                    i += 2;
                    continue;
                }
            }
            long[] one = new long[1];
            one[0] = id;
            atoms.add(one);
            i++;
        }
        return atoms;
    }

    /**
     * The method sums stored tokens for pinned ids.
     *
     * @param region The region supplies the index.
     * @param ids The ids are the pinned rows.
     * @return The count is the token sum.
     */
    private int tokenIds(Region region, long[] ids) {
        int sum = 0;
        if (ids == null) {
            return 0;
        }
        for (int i = 0; i < ids.length; i++) {
            sum += storedTokens(region, ids[i]);
        }
        return sum;
    }

    /**
     * The method sums stored tokens for tail atoms.
     *
     * @param region The region supplies the index.
     * @param atoms The atoms are the tail.
     * @return The count is the token sum.
     */
    private int tokenAtoms(Region region, ArrayList<long[]> atoms) {
        int sum = 0;
        for (int i = 0; i < atoms.size(); i++) {
            long[] atom = atoms.get(i);
            for (int a = 0; a < atom.length; a++) {
                sum += storedTokens(region, atom[a]);
            }
        }
        return sum;
    }

    /**
     * The method returns the token count of one stored message.
     * A count above zero is used as it is.
     * A missing count uses {@link HeuristicTokenCounter#count}.
     *
     * @param region The region supplies the index.
     * @param id The id is the message sequence.
     * @return The count is at least {@code 0}.
     */
    private int storedTokens(Region region, long id) {
        IndexEntry entry = region.index.get(id);
        if (entry == null) {
            return 0;
        }
        if (entry.getTokenCount() > 0) {
            return entry.getTokenCount();
        }
        DecodedFrame frame = readMessage(region.id, id);
        String text = frame == null ? "" : frame.getText();
        return new HeuristicTokenCounter().count(text);
    }

    /**
     * The method reports whether an atom carries the mood mark.
     *
     * @param region The region supplies the text.
     * @param atom The atom is one tail group.
     * @param moodMark The mark is the mood text, or null.
     * @return The flag is true when the mark appears in the atom.
     */
    private boolean moodAtom(Region region, long[] atom, String moodMark) {
        if (moodMark == null || moodMark.length() == 0) {
            return false;
        }
        for (int i = 0; i < atom.length; i++) {
            DecodedFrame frame = readMessage(region.id, atom[i]);
            if (frame != null && frame.getText() != null && frame.getText().indexOf(moodMark) >= 0) {
                return true;
            }
        }
        return false;
    }

    // @aa-sdd PK-499 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-1306 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-501 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-900 docs/specs/common/stages.spec.md
    // @aa-sdd PK-514 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-1382 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-817 docs/specs/common/roux.spec.md
    // @aa-sdd PK-520 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-824 docs/specs/common/roux.spec.md
    // @aa-sdd PK-827 docs/specs/common/roux.spec.md
    // @aa-sdd PK-504 docs/specs/java/org/roux/roux.spec.md
    private static class Region {
        private final String id;
        private final ReentrantLock lock;
        private final PinTable pins;
        private final MonotonicIndex index;
        private final Map<Integer, Page> pages;
        private final Map<RouxId, Long> byId;
        private Page hot;
        private long nextMessageId;
        private int nextPageId;
        private boolean regionPinned;
        private int headCount;
        private long[] headSeqs;
        private LockToken operatorLock;
        private UUID regionUuid;
        private long createdEpochMs;
        private boolean closed;
        private boolean restored;
        private ClipRecord clip;

        /**
         * The constructor opens page {@code 0} as the hot page.
         * The index, the pin table, and both maps are empty.
         * The next message id is {@code 1}, and the next page id is {@code 1}.
         * The region is unpinned, the head prefix is empty, and the region is not closed.
         *
         * @param id The id is the conversation id.
         * @param pageBytes The page size is the capacity of the first hot page.
         */
        private Region(String id, int pageBytes) {
            this.id = id;
            this.lock = new ReentrantLock();
            this.pins = new PinTable();
            this.index = new MonotonicIndex();
            this.pages = new LinkedHashMap<Integer, Page>();
            this.byId = new HashMap<RouxId, Long>();
            this.hot = new Page(0, pageBytes);
            this.pages.put(Integer.valueOf(0), this.hot);
            this.nextMessageId = 1;
            this.nextPageId = 1;
            this.regionPinned = false;
            this.headCount = 0;
            this.headSeqs = new long[0];
            this.regionUuid = UUID.randomUUID();
            this.createdEpochMs = System.currentTimeMillis();
            this.closed = false;
            this.restored = false;
        }
    }
}
/*****************************************************************************************
 * End of Class: RouxEngine.java 082626
 ****************************************************************************************/
