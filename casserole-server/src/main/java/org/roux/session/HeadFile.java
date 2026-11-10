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

// @aa-sdd PK-0860 PK-0907 PK-0954 PK-1001 PK-1048 PK-1095 PK-1142 PK-1189 PK-1236 PK-1283 PK-1330 PK-1377 PK-1424 PK-1471 PK-1518 PK-1565 PK-1613 PK-1660 PK-1707 PK-1754 PK-1801 PK-1848 PK-1895 PK-1942 PK-1989 PK-2036 PK-2084 PK-2131 PK-2178 PK-2225 PK-2273 PK-2321 PK-2369 PK-2416 PK-2463 PK-2511 PK-2558 PK-2605 PK-2652 PK-2700 PK-2747 PK-2795 PK-2842 PK-2889 PK-2936 PK-2983 PK-3030 PK-3077 PK-3124 PK-3171 PK-3219 PK-3266 PK-3313 PK-3361 PK-3408 PK-3455 docs/specs/java/org/casserole/chat/cmd/cmd.spec.md
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
