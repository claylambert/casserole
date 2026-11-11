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

package org.roux.spill;

import java.io.IOException;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import org.roux.RouxProperties;
import org.roux.page.Page;
import org.roux.page.PageHeader;
import org.springframework.stereotype.Component;

/**
 * The SpillWriter writes one page to its own file.
 * The file is {@code {dir}/{regionId}/{pageId}.pg}.
 * {@link RegionPack} is the writer that uses one {@code pages.bin} instead.
 * This writer still emits one file per page id when a caller uses it.
 * The checksum is written before the bytes are copied.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-08-25)
 */
@Component
public class SpillWriter {

    private final RouxProperties properties;

    /**
     * The constructor stores the settings.
     *
     * @param properties The properties are the settings this writer reads.
     */
    public SpillWriter(RouxProperties properties) {
        this.properties = properties;
    }

    /**
     * The method reports whether spill is enabled.
     * A missing spill block returns false.
     *
     * @return The flag is true when spill is enabled.
     */
    public boolean isEnabled() {
        return properties.getSpill() != null && properties.getSpill().isEnabled();
    }

    /**
     * The method returns the directory for one region.
     * A missing directory setting uses the user home plus {@code /.casserole/roux}.
     *
     * @param regionId The region id is the folder name under that directory.
     * @return The path is the region directory.
     */
    public Path regionDir(String regionId) {
        String root = properties.getSpill() == null ? null : properties.getSpill().getDir();
        if (root == null || root.length() == 0) {
            root = System.getProperty("user.home") + "/.casserole/roux";
        }
        return Path.of(root, regionId);
    }

    /**
     * The method writes the used bytes of one page.
     * The checksum is written first.
     * The file is created or truncated.
     * The channel is forced before it is closed.
     *
     * @param regionId The region id is the folder name.
     * @param page The page is the hot page to write.
     * @return The path is the file that was written.
     * @throws IOException The write failed.
     */
    public Path write(String regionId, Page page) throws IOException {
        Path dir = regionDir(regionId);
        Files.createDirectories(dir);
        Path file = dir.resolve(page.getPageId() + ".pg");
        int used = page.usedBytes();
        PageHeader.writeCrc(page.getSegment());
        byte[] copy = new byte[used];
        MemorySegment.copy(page.getSegment(), ValueLayout.JAVA_BYTE, 0, copy, 0, used);
        ByteBuffer buf = ByteBuffer.wrap(copy);
        FileChannel channel = FileChannel.open(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
                StandardOpenOption.TRUNCATE_EXISTING);
        try {
            while (buf.hasRemaining()) {
                channel.write(buf);
            }
            channel.force(true);
        } finally {
            channel.close();
        }
        return file;
    }

    /**
     * The method copies bytes from a spill file onto the heap.
     * A short file leaves the unread tail as zeros.
     *
     * @param file The file is the spill file.
     * @param offset The offset is the first byte to read.
     * @param length The length is the number of bytes to copy.
     * @return The array is the heap copy.
     * @throws IOException The read failed.
     */
    public byte[] read(Path file, int offset, int length) throws IOException {
        byte[] dest = new byte[length];
        FileChannel channel = FileChannel.open(file, StandardOpenOption.READ);
        try {
            ByteBuffer buf = ByteBuffer.wrap(dest);
            int pos = offset;
            while (buf.hasRemaining()) {
                int n = channel.read(buf, pos);
                if (n < 0) {
                    break;
                }
                pos += n;
            }
        } finally {
            channel.close();
        }
        return dest;
    }

    /**
     * The method deletes the files in a region's spill directory and then deletes the directory.
     * A missing directory returns without an error.
     * An input-output error is ignored.
     *
     * @param regionId The region id is the folder name.
     */
    public void deleteRegion(String regionId) {
        Path dir = regionDir(regionId);
        if (!Files.isDirectory(dir)) {
            return;
        }
        try {
            DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
            try {
                for (Path path : stream) {
                    Files.deleteIfExists(path);
                }
            } finally {
                stream.close();
            }
            Files.deleteIfExists(dir);
        } catch (IOException ignored) {
            // ignore
        }
    }
}
/*****************************************************************************************
 * End of Class: SpillWriter.java 082526
 ****************************************************************************************/
