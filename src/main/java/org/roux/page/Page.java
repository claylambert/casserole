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

// @aa-sdd PK-0788 docs/specs/java/org/roux/roux.spec.md
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
