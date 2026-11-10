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
import java.nio.file.Files;
import java.nio.file.Path;
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
import org.roux.session.OpenResult;
import org.roux.session.RegionManifest;
import org.roux.session.HeadFile;
import org.roux.spill.RegionPack;
import org.roux.token.HeuristicTokenCounter;
import org.roux.tel.RouxCounters;
import org.roux.toon.ToonCodec;
import org.springframework.stereotype.Component;

import jakarta.annotation.PreDestroy;

// @aa-sdd PK-0745 PK-0781 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-0855 PK-0902 PK-0949 PK-0996 PK-1043 PK-1090 PK-1137 PK-1184 PK-1231 PK-1278 PK-1325 PK-1372 PK-1419 PK-1466 PK-1513 PK-1560 PK-1608 PK-1655 PK-1702 PK-1749 PK-1796 PK-1843 PK-1890 PK-1937 PK-1984 PK-2031 PK-2079 PK-2126 PK-2173 PK-2220 PK-2268 PK-2316 PK-2364 PK-2411 PK-2458 PK-2506 PK-2553 PK-2600 PK-2647 PK-2695 PK-2742 PK-2790 PK-2837 PK-2884 PK-2931 PK-2978 PK-3025 PK-3072 PK-3119 PK-3166 PK-3214 PK-3261 PK-3308 PK-3356 PK-3403 PK-3450 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
/**
 * Roux is the paged memory beside the terminal.
 * The RouxEngine type is that memory.
 * One region is stored for each conversation id.
 * The type implements {@link MemoryInspector}, {@link MemoryLayoutManager}, {@link MemoryStateController}, and {@link MemoryLifecycleManager}.
 * A region holds the hot page, the off-heap index, and the map from a {@link RouxId} to a message id.
 * The hot page receives writes.
 * A disk region publishes through {@link RegionPack}.
 * A memory-only region stays mapped and does not take a pack lock.
 *
 * A caller opens a region, appends a message, reads a window, and closes the region.
 * The type refuses an empty conversation id.
 * The type refuses a snapshot, a heap dump, and defragment in this version.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
 * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
 * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
 * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
 * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
 * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
 * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
 * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
 * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
 * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
 * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
 * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
 * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
 * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
 * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
 * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
 * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
 * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
 * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
 *
 * @author clayl (2026-08-25)
 */
@Component
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
     * // @aa-sdd PK-0472 PK-0473 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-3469 PK-3474 PK-3484 PK-3470 PK-3475 PK-3485 PK-3473 PK-3478 PK-3488 docs/specs/java/org/roux/roux.spec.md
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
        mapLock.lock();
        try {
            Region existing = regions.get(regionId);
            if (existing != null) {
                return new AllocationResult(true, describe(existing), AllocationStatus.SUCCESS, "");
            }
            Region region = new Region(regionId, pageBytes());
            regions.put(regionId, region);
            counters.pagesAllocated().incrementAndGet();
            loadHead(region);
            return new AllocationResult(true, describe(region), AllocationStatus.SUCCESS, "");
        } finally {
            mapLock.unlock();
        }
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
     * A frame larger than {@link #maxFrameBytes} returns {@link OperationStatus#CORRUPTED_SEGMENT} and {@code frame larger than max-frame-bytes}.
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
        int total = FrameHeader.totalLength(payload.length);
        int maxFrame = maxFrameBytes();
        if (total > maxFrame) {
            return OperationResult.fail(OperationStatus.CORRUPTED_SEGMENT, "frame larger than max-frame-bytes");
        }
        RouxId rouxId = idGenerator.next();
        long waitStart = System.nanoTime();
        region.lock.lock();
        counters.lockWaitNanos().addAndGet(System.nanoTime() - waitStart);
        try {
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
            long messageId = region.nextMessageId;
            Page page = region.hot;
            region.pins.pin(page.getPageId());
            try {
                page.beginWrite();
                FrameHeader.write(page.getSegment(), offset, total, FrameType.MSG, role, messageId, timestampEpochMs,
                        tokenCount, rouxId, payload);
                int used = offset + total;
                PageHeader.setUsedBytes(page.getSegment(), used);
                PageHeader.setFrameCount(page.getSegment(), PageHeader.frameCount(page.getSegment()) + 1);
                if (PageHeader.frameCount(page.getSegment()) == 1) {
                    PageHeader.setFirstId(page.getSegment(), messageId);
                }
                PageHeader.setLastId(page.getSegment(), messageId);
                page.endWrite();
                region.index.append(messageId, page.getPageId(), offset, total, tokenCount, timestampEpochMs, role,
                        FrameType.MSG, (short) 0, rouxId);
                region.byId.put(rouxId, Long.valueOf(messageId));
                region.nextMessageId = messageId + 1;
                counters.framesAppended().incrementAndGet();
                counters.indexEntries().incrementAndGet();
                recountUsed();
                if ("append".equals(properties.normalizedDurability()) && regionPack.isEnabled()) {
                    checkpointHot(region);
                }
                return OperationResult.ok("");
            } finally {
                region.pins.unpin(page.getPageId());
            }
        } finally {
            region.lock.unlock();
        }
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
            counters.pagesAllocated().incrementAndGet();
            if (properties.isRamOnly()) {
                recountUsed();
                return;
            }
            regionPack.wipePack(regionId);
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
     * // @aa-sdd PK-3471 PK-3476 PK-3486 docs/specs/java/org/roux/roux.spec.md
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
        Path dir = regionPack.regionDir(regionId);
        if (!pack && !manFile) {
            allocateRegion(regionId, 0);
            Region created = require(regionId);
            if (created == null) {
                regionPack.unlock(regionId);
                return OpenResult.refused("region not found");
            }
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
     * Persist off destroys the region and deletes the region directory.
     * Both disk paths mark the region closed, remove the id, and return success with {@code closed}.
     *
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
                regionPack.unlock(regionId);
            } else {
                destroyRegion(region);
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
            return OpenResult.refused("roux pack incomplete or corrupt; delete " + regionPack.regionDir(regionId)
                    + " or revert yaml");
        }
        int hotId;
        boolean repair;
        String refuse = "roux pack incomplete or corrupt; delete " + regionPack.regionDir(regionId)
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
                try {
                    MemorySegment hs = headerArena.allocate(PageHeader.SIZE, 8);
                    MemorySegment.copy(header, 0, hs, ValueLayout.JAVA_BYTE, 0, header.length);
                    used = PageHeader.usedBytes(hs);
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
                    scanFrames(region, hot.getSegment(), pageId, used);
                } else {
                    Page spilled = Page.spilled(pageId, pageBytes(), pack);
                    region.pages.put(Integer.valueOf(pageId), spilled);
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
        } catch (IOException ex) {
            destroyRegion(region);
            return OpenResult.refused("roux pack incomplete or corrupt; delete " + regionPack.regionDir(regionId)
                    + " or revert yaml");
        }
        if (region.hot == null) {
            destroyRegion(region);
            return OpenResult.refused("roux pack incomplete or corrupt; delete " + regionPack.regionDir(regionId)
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
        return OpenResult.restored(region.index.size(), region.regionUuid);
    }

    /**
     * The method counts page headers in the pack from page {@code 0}.
     * The file length is not the count.
     * A slot whose header does not fit in the file returns that page id.
     * A header without the page magic returns that page id.
     * Magic whose version is not {@link PageHeader#VERSION}, or whose page id is not this slot, returns {@code -1}.
     * A slot offset that overflows returns {@code -1}.
     *
     * // @aa-sdd PK-3472 PK-3477 PK-3487 docs/specs/java/org/roux/roux.spec.md
     *
     * @param regionId The region id is the conversation id.
     * @return The count is the number of accepted slots, or {@code -1} when a header is refused.
     * @throws IOException The pack file could not be sized or read.
     */
    private int walkSlots(String regionId) throws IOException {
        long size = Files.size(regionPack.packFile(regionId));
        long pitch = pageBytes();
        int pageId = 0;
        while (pageId >= 0) {
            long at = (long) pageId * pitch;
            if (at < 0) {
                return -1;
            }
            if (size < at + PageHeader.SIZE) {
                return pageId;
            }
            byte[] header = regionPack.readRange(regionId, pageId, 0, PageHeader.SIZE);
            Arena arena = Arena.ofConfined();
            try {
                MemorySegment seg = arena.allocate(PageHeader.SIZE, 8);
                MemorySegment.copy(header, 0, seg, ValueLayout.JAVA_BYTE, 0, header.length);
                if (!PageHeader.hasMagic(seg)) {
                    return pageId;
                }
                int version = seg.get(RouxLayout.SHORT_LE, 4) & 0xFFFF;
                if (version != PageHeader.VERSION || PageHeader.pageId(seg) != pageId) {
                    return -1;
                }
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
     * A message id from {@code 1} through the index size retargets that row's page, offset, length, and token count.
     * Any other message id stops this page.
     *
     * @param region The region receives the index rows.
     * @param seg The segment is the page bytes.
     * @param pageId The page id is stored on each accepted row.
     * @param used The used count is the last byte to read.
     */
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
                RouxId rid = RouxId.read(seg, offset + FrameHeader.OFF_ID_HI);
                region.index.append(seq, pageId, (int) offset, total, tokens,
                        FrameHeader.timestamp(seg, offset), FrameHeader.role(seg, offset),
                        FrameHeader.type(seg, offset), (short) 0, rid);
                region.byId.put(rid, Long.valueOf(seq));
            } else if (seq >= 1L && seq <= region.index.size()) {
                region.index.retarget(seq, pageId, (int) offset, total, tokens);
            } else {
                break;
            }
            offset += total;
        }
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
            RegionManifest.write(regionPack.regionDir(region.id), man);
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
     * A match is decoded by {@link ToonCodec}.
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
        int crc = java.nio.ByteBuffer.wrap(frameBytes).order(java.nio.ByteOrder.LITTLE_ENDIAN)
                .getInt(FrameHeader.OFF_CRC);
        if (!FrameHeader.crcMatches(payload, crc)) {
            counters.dirtyFrames().incrementAndGet();
            return null;
        }
        return ToonCodec.decode(payload, entry.getMessageId(), entry.getRole(), entry.getTimestamp(),
                entry.getTokenCount());
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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
     * // @aa-sdd PK-0073 PK-0079 PK-0104 PK-0108 PK-0113 PK-0127 PK-0133 PK-0136 PK-0137 PK-0166 PK-0257 PK-0260 PK-0261 PK-0404 PK-0445 PK-0456 PK-0460 PK-0542 docs/specs/java/org/casserole/casserole.spec.md
     * // @aa-sdd PK-0085 PK-0121 PK-0126 PK-0150 PK-0160 PK-0165 PK-0167 PK-0168 PK-0274 PK-0283 PK-0284 PK-0385 PK-0412 PK-0415 PK-0416 PK-0453 PK-0462 PK-3514 PK-3522 PK-3548 PK-3576 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3710 PK-3717 PK-3728 PK-3738 PK-3739 PK-3753 PK-3754 PK-3765 PK-3773 PK-3787 PK-3789 PK-3790 PK-3792 PK-3793 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-4115 PK-4154 PK-4437 PK-7815 PK-7864 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
     * // @aa-sdd PK-0132 PK-0256 PK-4448 PK-4481 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
     * // @aa-sdd PK-0151 PK-0202 PK-0321 PK-3729 PK-3730 docs/specs/rust/aetheris/aetheris-client/src/mood.spec.md
     * // @aa-sdd PK-0164 PK-0530 docs/specs/java/org/casserole/pool/pool.spec.md
     * // @aa-sdd PK-0458 PK-3817 PK-4114 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4240 PK-4331 PK-4371 PK-4410 PK-4411 PK-4413 PK-4414 PK-4419 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
     * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
     * // @aa-sdd PK-0466 PK-3547 PK-3552 PK-3582 PK-3698 docs/specs/java/org/aetheris/ssh/ssh.spec.md
     * // @aa-sdd PK-0487 PK-0533 PK-0534 PK-0538 PK-0544 PK-0546 PK-0552 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3546 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
     * // @aa-sdd PK-0510 PK-3794 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
     * // @aa-sdd PK-0535 PK-0537 PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
     * // @aa-sdd PK-0539 PK-0553 docs/specs/java/org/casserole/ollama/ollama.spec.md
     * // @aa-sdd PK-0540 docs/specs/java/org/casserole/lamina/spring/spring.spec.md
     * // @aa-sdd PK-0541 docs/specs/java/org/casserole/app/app.spec.md
     * // @aa-sdd PK-0547 PK-0548 PK-0551 docs/specs/java/org/casserole/lamina/lamina.spec.md
     * // @aa-sdd PK-0550 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/chat/chat.spec.md
     * // @aa-sdd PK-0558 PK-0559 docs/specs/java/org/casserole/format/format.spec.md
     * // @aa-sdd PK-0560 docs/specs/java/org/casserole/format/markdown/markdown.spec.md
     * // @aa-sdd PK-0734 PK-0758 PK-0759 PK-3460 PK-3465 PK-3468 PK-3480 docs/specs/java/org/roux/model/model.spec.md
     * // @aa-sdd PK-0735 PK-0744 PK-0757 PK-0780 PK-0793 PK-0794 PK-0814 PK-0816 PK-3459 PK-3479 PK-3482 PK-3489 docs/specs/java/org/roux/roux.spec.md
     * // @aa-sdd PK-0815 PK-3467 docs/specs/java/org/roux/spill/spill.spec.md
     * // @aa-sdd PK-3464 docs/specs/java/org/roux/index/index.spec.md
     * // @aa-sdd PK-3466 docs/specs/java/org/roux/tel/tel.spec.md
     * // @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/session/session.spec.md
     * // @aa-sdd PK-3490 docs/specs/java/org/roux/frame/frame.spec.md
     * // @aa-sdd PK-4166 docs/specs/java/org/casserole/mood/mood.spec.md
     * // @aa-sdd PK-4415 PK-4422 docs/specs/java/org/aetheris/vault/vault.spec.md
     * // @aa-sdd PK-4436 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
     * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
     * // @aa-sdd PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/heatbar.spec.md
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

    // @aa-sdd PK-0856 PK-0903 PK-0950 PK-0997 PK-1044 PK-1091 PK-1138 PK-1185 PK-1232 PK-1279 PK-1326 PK-1373 PK-1420 PK-1467 PK-1514 PK-1561 PK-1609 PK-1656 PK-1703 PK-1750 PK-1797 PK-1844 PK-1891 PK-1938 PK-1985 PK-2032 PK-2080 PK-2127 PK-2174 PK-2221 PK-2269 PK-2317 PK-2365 PK-2412 PK-2459 PK-2507 PK-2554 PK-2601 PK-2648 PK-2696 PK-2743 PK-2791 PK-2838 PK-2885 PK-2932 PK-2979 PK-3026 PK-3073 PK-3120 PK-3167 PK-3215 PK-3262 PK-3309 PK-3357 PK-3404 PK-3451 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
    /**
     * The SinkCut type holds one selection from {@link ReichenbachSink}.
     * The ordinals follow the selection order.
     * The headOver flag is true when the pin and the head exceed the allowance.
     * The tokenSum includes the live line when that line is in the selection.
     * A refused selection is a null return from {@link #selectCut} or {@link #selectTape}.
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
     * A frame larger than {@link #maxFrameBytes} returns {@link OperationStatus#CORRUPTED_SEGMENT} and {@code frame larger than max-frame-bytes}.
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
            int total = FrameHeader.totalLength(payload.length);
            int maxFrame = maxFrameBytes();
            if (total > maxFrame) {
                return OperationResult.fail(OperationStatus.CORRUPTED_SEGMENT, "frame larger than max-frame-bytes");
            }
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
                FrameHeader.write(page.getSegment(), offset, total, FrameType.MSG, entry.getRole(), seq,
                        entry.getTimestamp(), tokenCount, entry.getRouxId(), payload);
                int used = offset + total;
                PageHeader.setUsedBytes(page.getSegment(), used);
                PageHeader.setFrameCount(page.getSegment(), PageHeader.frameCount(page.getSegment()) + 1);
                long last = PageHeader.lastId(page.getSegment());
                if (seq >= last) {
                    PageHeader.setLastId(page.getSegment(), seq);
                }
                page.endWrite();
                region.index.retarget(seq, page.getPageId(), offset, total, tokenCount);
                counters.framesAppended().incrementAndGet();
                recountUsed();
                if ("append".equals(properties.normalizedDurability()) && regionPack.isEnabled()) {
                    checkpointHot(region);
                }
                return OperationResult.ok("");
            } finally {
                region.pins.unpin(page.getPageId());
            }
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
        HeadFile file = HeadFile.read(regionPack.regionDir(region.id));
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
            HeadFile.write(regionPack.regionDir(region.id), region.headCount, region.headSeqs);
        } catch (IOException ignored) {
            // the RAM prefix stays. The next lock writes again.
        }
    }

    // @aa-sdd PK-0857 PK-0904 PK-0951 PK-0998 PK-1045 PK-1092 PK-1139 PK-1186 PK-1233 PK-1280 PK-1327 PK-1374 PK-1421 PK-1468 PK-1515 PK-1562 PK-1610 PK-1657 PK-1704 PK-1751 PK-1798 PK-1845 PK-1892 PK-1939 PK-1986 PK-2033 PK-2081 PK-2128 PK-2175 PK-2222 PK-2270 PK-2318 PK-2366 PK-2413 PK-2460 PK-2508 PK-2555 PK-2602 PK-2649 PK-2697 PK-2744 PK-2792 PK-2839 PK-2886 PK-2933 PK-2980 PK-3027 PK-3074 PK-3121 PK-3168 PK-3216 PK-3263 PK-3310 PK-3358 PK-3405 PK-3452 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
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
        }
    }
}
/*****************************************************************************************
 * End of Class: RouxEngine.java 082626
 ****************************************************************************************/
