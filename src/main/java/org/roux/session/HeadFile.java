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

package org.roux.session;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

// @aa-sdd GK-1056 aetheris-stage-2-finessing.001.md FINESSE:f-222
/**
 * The HeadFile is the {@code head.yaml} file beside {@code manifest.bin}.
 * The file name is {@code head.yaml}.
 * A readable file has {@code version: 1}, a {@code count}, and a {@code seqs} list.
 * {@code count} is the stored head count.
 * {@code seqs} is the list of locked message ids.
 * A missing file is no head.
 * A version other than {@code 1} is no head.
 * A count of {@code 0} is no head.
 * An empty list is no head.
 * A number that cannot be parsed is no head.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#HeadFile reichenbach-sink.draft.001.md
 *
 * @author clayl (2026-11-04)
 */
public final class HeadFile {

    public static final String FILE_NAME = "head.yaml";

    public final int count;
    public final long[] seqs;

    /**
     * The constructor stores the count and the message ids.
     *
     * @param count The count is the stored head count.
     * @param seqs The ids are the locked message ids.
     */
    private HeadFile(int count, long[] seqs) {
        this.count = count;
        this.seqs = seqs;
    }

    /**
     * The method reads {@code head.yaml} from a region directory.
     * A null directory returns a count of {@code 0} and an empty list.
     * A missing file returns that same empty head.
     * A read error returns that same empty head.
     * A leading byte-order mark is removed before the lines are read.
     * A trailing carriage return on a line is removed.
     *
     * @param dir The directory is the region directory.
     * @return The file is the parsed head, or an empty head.
     */
    public static HeadFile read(Path dir) {
        if (dir == null) {
            return none();
        }
        Path file = dir.resolve(FILE_NAME);
        if (!Files.isRegularFile(file)) {
            return none();
        }
        String text;
        try {
            text = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            return none();
        }
        if (text.startsWith("\uFEFF")) {
            text = text.substring(1);
        }
        String version = "";
        int count = 0;
        boolean sawCount = false;
        ArrayList<Long> seqs = new ArrayList<Long>();
        boolean inSeqs = false;
        String[] lines = text.split("\n", -1);
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (line.endsWith("\r")) {
                line = line.substring(0, line.length() - 1);
            }
            String trimmed = line.trim();
            if (trimmed.length() == 0) {
                continue;
            }
            if (trimmed.startsWith("version:")) {
                version = trimmed.substring("version:".length()).trim();
                inSeqs = false;
                continue;
            }
            if (trimmed.startsWith("count:")) {
                sawCount = true;
                try {
                    count = Integer.parseInt(trimmed.substring("count:".length()).trim());
                } catch (NumberFormatException ex) {
                    return none();
                }
                inSeqs = false;
                continue;
            }
            if (trimmed.equals("seqs:")) {
                inSeqs = true;
                continue;
            }
            if (inSeqs && trimmed.startsWith("-")) {
                String raw = trimmed.substring(1).trim();
                try {
                    seqs.add(Long.valueOf(Long.parseLong(raw)));
                } catch (NumberFormatException ex) {
                    return none();
                }
            }
        }
        if (!"1".equals(version) || !sawCount || count <= 0 || seqs.isEmpty()) {
            return none();
        }
        long[] ids = new long[seqs.size()];
        for (int i = 0; i < seqs.size(); i++) {
            ids[i] = seqs.get(i).longValue();
        }
        return new HeadFile(count, ids);
    }

    /**
     * The method writes {@code head.yaml}.
     * A null directory returns without writing.
     * The directory is created when it is absent.
     * The bytes are written to {@code head.yaml.tmp} and then moved onto {@code head.yaml}.
     * A failed atomic move deletes the destination and moves the temp file again.
     * The temp file is deleted after the move.
     *
     * @param dir The directory is the region directory.
     * @param count The count is the head count to write.
     * @param seqs The ids are the locked message ids to write.
     * @throws IOException The write or the move failed.
     */
    public static void write(Path dir, int count, long[] seqs) throws IOException {
        if (dir == null) {
            return;
        }
        Files.createDirectories(dir);
        StringBuilder body = new StringBuilder();
        body.append("version: 1\n");
        body.append("count: ").append(count).append('\n');
        body.append("seqs:\n");
        if (seqs != null) {
            for (int i = 0; i < seqs.length; i++) {
                body.append("- ").append(seqs[i]).append('\n');
            }
        }
        Path dest = dir.resolve(FILE_NAME);
        Path tmp = dir.resolve(FILE_NAME + ".tmp");
        Files.write(tmp, body.toString().getBytes(StandardCharsets.UTF_8));
        try {
            Files.move(tmp, dest, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
        } catch (IOException ex) {
            Files.deleteIfExists(dest);
            Files.move(tmp, dest, StandardCopyOption.REPLACE_EXISTING);
        } finally {
            Files.deleteIfExists(tmp);
        }
    }

    /**
     * The method builds an empty head.
     * The count is {@code 0}.
     * The id list is empty.
     *
     * @return The file is an empty head.
     */
    private static HeadFile none() {
        return new HeadFile(0, new long[0]);
    }
}
/*****************************************************************************************
 * End of Class: HeadFile.java 110426
 ****************************************************************************************/
