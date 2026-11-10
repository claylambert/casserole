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

package org.roux.page;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.file.Path;

// @aa-sdd PK-0752 PK-0788 docs/specs/java/org/roux/roux.spec.md
/**
 * The Page is one fixed block of bytes.
 * A hot page is mapped and is still receiving writes.
 * A spilled page keeps a file path and has no mapped bytes.
 * A resident page is a spilled page mapped back into memory.
 * {@link #residency} reports {@code HOT}, {@code RESIDENT}, or {@code SPILLED}.
 *
 * The constructor maps a new hot page and writes its header.
 * {@link #mapFromBytes} copies a spilled slot back into memory.
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
public class Page {

    private final int pageId;
    private Arena arena;
    private MemorySegment segment;
    private final int capacity;
    private Path file;
    private boolean spilled;
    private int generation;

    /**
     * The constructor allocates a hot page in memory.
     * The bytes are aligned to {@code 8}.
     * {@link PageHeader#init} writes the header.
     *
     * @param pageId The page id is the number of this page.
     * @param capacity The capacity is the page size in bytes.
     */
    public Page(int pageId, int capacity) {
        this.pageId = pageId;
        this.capacity = capacity;
        this.arena = Arena.ofShared();
        this.segment = arena.allocate(capacity, 8);
        this.file = null;
        this.spilled = false;
        this.generation = 0;
        PageHeader.init(segment, pageId, capacity);
    }

    /**
     * The method builds a spilled page with no mapped bytes.
     *
     * @param pageId The page id is the number of this page.
     * @param capacity The capacity is the page size in bytes.
     * @param file The file is the spill path.
     * @return The page is spilled.
     */
    public static Page spilled(int pageId, int capacity, Path file) {
        Page page = new Page(pageId, capacity, file);
        return page;
    }

    /**
     * The constructor stores a spilled page.
     * The segment is left null.
     * The spilled flag is true.
     *
     * @param pageId The page id is the number of this page.
     * @param capacity The capacity is the page size in bytes.
     * @param file The file is the spill path.
     */
    private Page(int pageId, int capacity, Path file) {
        this.pageId = pageId;
        this.capacity = capacity;
        this.arena = null;
        this.segment = null;
        this.file = file;
        this.spilled = true;
        this.generation = 0;
    }

    /**
     * The method returns the page id.
     *
     * @return The id is the number of this page.
     */
    public int getPageId() {
        return pageId;
    }

    /**
     * The method returns the page capacity.
     *
     * @return The capacity is the page size in bytes.
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * The method returns the mapped segment.
     *
     * @return The segment is the live bytes, or null when the page is not mapped.
     */
    public MemorySegment getSegment() {
        return segment;
    }

    /**
     * The method returns the spill path.
     *
     * @return The path is the spill file, or null.
     */
    public Path getFile() {
        return file;
    }

    /**
     * The method stores the spill path.
     *
     * @param file The file is the spill path.
     */
    public void setFile(Path file) {
        this.file = file;
    }

    /**
     * The method reports whether the page is spilled.
     *
     * @return The flag is true when the page is marked spilled.
     */
    public boolean isSpilled() {
        return spilled;
    }

    /**
     * The method returns the used-byte count from the header.
     * A page with no segment returns {@code 0}.
     *
     * @return The count is the used bytes.
     */
    public int usedBytes() {
        if (segment == null) {
            return 0;
        }
        return PageHeader.usedBytes(segment);
    }

    /**
     * The method returns the unused bytes.
     * The unused count is the capacity minus {@link #usedBytes}.
     *
     * @return The count is the bytes still free.
     */
    public int remaining() {
        return capacity - usedBytes();
    }

    /**
     * The method returns the generation.
     * The generation is the counter a reader uses to notice a write in progress.
     *
     * @return The generation is the current counter.
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * The method starts a write.
     * The generation becomes odd.
     * The same value is written into the page header.
     */
    public void beginWrite() {
        generation++;
        if ((generation & 1) == 0) {
            generation++;
        }
        PageHeader.setGeneration(segment, generation);
    }

    /**
     * The method ends a write.
     * The generation becomes even.
     * The same value is written into the page header.
     */
    public void endWrite() {
        generation++;
        if ((generation & 1) != 0) {
            generation++;
        }
        PageHeader.setGeneration(segment, generation);
    }

    /**
     * The method drops the mapped bytes.
     * The spilled flag becomes true.
     * A live arena is closed.
     */
    public void unmapRam() {
        spilled = true;
        if (arena != null && arena.scope().isAlive()) {
            arena.close();
        }
        arena = null;
        segment = null;
    }

    /**
     * The method maps a copy of packed bytes into a new arena.
     * The copy is limited to the page capacity.
     * The spilled flag stays true.
     * The file path is not changed.
     *
     * @param used The bytes are the valid prefix to copy.
     */
    public void mapFromBytes(byte[] used) {
        if (arena != null && arena.scope().isAlive()) {
            arena.close();
        }
        this.arena = Arena.ofShared();
        this.segment = arena.allocate(capacity, 8);
        int n = used.length;
        if (n > capacity) {
            n = capacity;
        }
        MemorySegment.copy(used, 0, this.segment, ValueLayout.JAVA_BYTE, 0, n);
        this.spilled = true;
    }

    /**
     * The method marks this page as the writable hot page.
     * The spilled flag becomes false.
     * Call this after {@link #mapFromBytes}.
     */
    public void adoptAsHot() {
        this.spilled = false;
    }

    /**
     * The method reports where the page bytes live.
     * A mapped page that is not spilled returns {@code HOT}.
     * A mapped page that is spilled returns {@code RESIDENT}.
     * A page with no segment returns {@code SPILLED}.
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
     * @return The word is {@code HOT}, {@code RESIDENT}, or {@code SPILLED}.
     */
    public String residency() {
        if (!spilled && segment != null) {
            return "HOT";
        }
        if (spilled && segment != null) {
            return "RESIDENT";
        }
        return "SPILLED";
    }

    /**
     * The method zeros the mapped bytes and closes the arena.
     * A page that is already unmapped is left unchanged.
     */
    public void close() {
        if (segment != null && arena != null && arena.scope().isAlive()) {
            segment.fill((byte) 0);
            arena.close();
        }
        arena = null;
        segment = null;
    }
}
/*****************************************************************************************
 * End of Class: Page.java 082526
 ****************************************************************************************/
