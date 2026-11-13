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
 * // @aa-sdd L40 aa-sdd-source-prose.md#SpillWriter roux-design.md
 *
 * @author clayl (2026-08-25)
 */
@Component
// @aa-sdd PK-526 docs/specs/java/org/roux/spill/spill.spec.md
// @aa-sdd GK-2316 roux-clip-range.md
// @aa-sdd GK-2412 spillover-semantics.md
// @aa-sdd PK-532 docs/specs/java/org/roux/spill/spill.spec.md
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
