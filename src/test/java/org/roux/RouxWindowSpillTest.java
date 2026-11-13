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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.roux.crypto.CryptoSink;
import org.roux.id.RouxIdGenerator;
import org.roux.model.MessageInspect;
import org.roux.model.OperationResult;
import org.roux.model.Role;
import org.roux.session.OpenResult;
import org.roux.session.RegionManifest;
import org.roux.spill.RegionPack;
import org.roux.tel.RouxCounters;

/**
 * The class checks the version 1.1 window in {@link RouxEngine}.
 * The head of the window moves forward.
 * The tail stays on the hot page.
 * A spilled head still reads.
 * The working set stays inside the resident cap.
 * A closed pack reopens when that region opens.
 * The scan of page headers is the bound on a reopen.
 *
 * The checks name that forward head, that hot tail, and that header scan.
 *
 * @author clayl (2026-11-02)
 */
// @aa-sdd L40 aa-sdd-source-prose.md FINESSE:f-169
// @aa-sdd L40 aa-sdd-source-prose.md#RouxWindowSpillTest roux-v1.md
// @aa-sdd GK-1097 roux-l2.md
// @aa-sdd GK-1524 aetheris-stage-2-finessing.001.md
// @aa-sdd PK-1108 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1108 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1108 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
// @aa-sdd GK-1929 aetheris-pre-plan-f-169.001.md
// @aa-sdd GK-1930 aetheris-pre-plan-f-169.001.md
// @aa-sdd GK-1934 aetheris-pre-plan-f-170.001.md
// @aa-sdd GK-3082 roux-l2.md
class RouxWindowSpillTest {

    private static final int PAGE = 512;
    private static final int FAT = 280;

    private final ArrayList<Seat> seats = new ArrayList<Seat>();

    /**
     * The method closes every {@link Seat} recorded by the test.
     * The method then clears that list.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because {@link Seat#close} declares that failure.
     */
    @AfterEach
    void closeSeats() throws IOException {
        for (int i = 0; i < seats.size(); i++) {
            seats.get(i).close();
        }
        seats.clear();
    }

    /**
     * The method arranges an empty region named from {@code empty} with spill on and two resident pages.
     * The method calls {@link RouxEngine#windowIds}.
     * The returned id list is empty.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void anEmptyWindowIsEmpty() throws IOException {
        Seat seat = seat("empty", true, 2, false);
        assertEquals(0, seat.engine.windowIds(seat.region, 4, false, 0).length);
    }

    /**
     * The method arranges four fat user messages in region {@code tail}.
     * The method calls {@link RouxEngine#memoryStatusLine} and {@link RouxEngine#inspect}.
     * The status contains {@code spilled=3}, {@code resident=1}, {@code hot=3}, and {@code seq=4}.
     * The spilled-page counter is at least {@code 3}.
     * Message {@code 4} is {@code HOT} and its text starts with {@code e}.
     * Message {@code 1} is {@code SPILLED} and its text starts with {@code b}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void theHotPageIsTheTailAfterSpill() throws IOException {
        Seat seat = seat("tail", true, 2, true);
        for (int i = 1; i <= 4; i++) {
            assertTrue(seat.append(Role.USER, 8, fat((char) ('a' + i))).isSuccess());
        }
        String line = seat.engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("spilled=3"));
        assertTrue(line.contains("resident=1"));
        assertTrue(line.contains("hot=3"));
        assertTrue(line.contains("seq=4"));
        assertTrue(seat.counters.pagesSpilled().get() >= 3L);
        MessageInspect tail = seat.engine.inspect(seat.region, 4L);
        assertEquals("HOT", tail.getResidency());
        assertTrue(tail.getText().startsWith("e"));
        MessageInspect head = seat.engine.inspect(seat.region, 1L);
        assertEquals("SPILLED", head.getResidency());
        assertTrue(head.getText().startsWith("b"));
    }

    /**
     * The method arranges four fat user messages in region {@code read}.
     * The method calls {@link RouxEngine#readMessage}, {@link RouxEngine#memoryStatusLine}, and {@link RouxEngine#inspect}.
     * Messages {@code 1}, {@code 2}, and {@code 4} start with {@code b}, {@code c}, and {@code e}.
     * The resident count is {@code 1} or {@code 2}.
     * The status does not contain {@code resident=3} or {@code resident=4}.
     * Message {@code 4} is {@code HOT}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void aSpilledHeadStillReadsInsideTheWorkingSet() throws IOException {
        Seat seat = seat("read", true, 2, true);
        for (int i = 1; i <= 4; i++) {
            assertTrue(seat.append(Role.USER, 8, fat((char) ('a' + i))).isSuccess());
        }
        assertTrue(seat.engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertTrue(seat.engine.readMessage(seat.region, 2L).getText().startsWith("c"));
        assertTrue(seat.engine.readMessage(seat.region, 4L).getText().startsWith("e"));
        String line = seat.engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("resident=2") || line.contains("resident=1"));
        assertFalse(line.contains("resident=3"));
        assertFalse(line.contains("resident=4"));
        MessageInspect tail = seat.engine.inspect(seat.region, 4L);
        assertEquals("HOT", tail.getResidency());
    }

    /**
     * The method arranges four short user messages in region {@code roll}.
     * The method calls {@link RouxEngine#windowIds}.
     * A window of two ids returns {@code 3} and {@code 4}.
     * A token budget of {@code 20} returns ids {@code 2}, {@code 3}, and {@code 4}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void theWindowRollsTheHeadAndKeepsTheTail() throws IOException {
        Seat seat = seat("roll", true, 2, true);
        for (int i = 1; i <= 4; i++) {
            assertTrue(seat.append(Role.USER, 10, "note-" + i).isSuccess());
        }
        long[] ids = seat.engine.windowIds(seat.region, 2, false, 0);
        assertEquals(2, ids.length);
        assertEquals(3L, ids[0]);
        assertEquals(4L, ids[1]);
        long[] budget = seat.engine.windowIds(seat.region, 0, false, 20);
        assertEquals(3, budget.length);
        assertEquals(2L, budget[0]);
        assertEquals(3L, budget[1]);
        assertEquals(4L, budget[2]);
    }

    /**
     * The method arranges two user-and-assistant pairs in region {@code atom}.
     * The method calls {@link RouxEngine#windowIds}.
     * A window of two ids returns {@code 3} and {@code 4}.
     * A window of one id still returns {@code 3} and {@code 4}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void aTurnAtomStaysTogetherWhenTheHeadRolls() throws IOException {
        Seat seat = seat("atom", true, 2, false);
        assertTrue(seat.append(Role.USER, 4, "ask-a").isSuccess());
        assertTrue(seat.append(Role.ASSISTANT, 4, "reply-a").isSuccess());
        assertTrue(seat.append(Role.USER, 4, "ask-b").isSuccess());
        assertTrue(seat.append(Role.ASSISTANT, 4, "reply-b").isSuccess());
        long[] pair = seat.engine.windowIds(seat.region, 2, false, 0);
        assertEquals(2, pair.length);
        assertEquals(3L, pair[0]);
        assertEquals(4L, pair[1]);
        long[] tight = seat.engine.windowIds(seat.region, 1, false, 0);
        assertEquals(2, tight.length);
        assertEquals(3L, tight[0]);
        assertEquals(4L, tight[1]);
    }

    /**
     * The method arranges a system message {@code host} and four user messages in region {@code pin}.
     * The method calls {@link RouxEngine#windowIds} with the pin flag true and a window of {@code 2}.
     * The ids are {@code 1}, {@code 4}, and {@code 5}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void aPinnedSystemStaysWhileTheHeadRolls() throws IOException {
        Seat seat = seat("pin", true, 2, false);
        assertTrue(seat.append(Role.SYSTEM, 3, "host").isSuccess());
        assertTrue(seat.append(Role.USER, 3, "one").isSuccess());
        assertTrue(seat.append(Role.USER, 3, "two").isSuccess());
        assertTrue(seat.append(Role.USER, 3, "three").isSuccess());
        assertTrue(seat.append(Role.USER, 3, "four").isSuccess());
        long[] ids = seat.engine.windowIds(seat.region, 2, true, 0);
        assertEquals(3, ids.length);
        assertEquals(1L, ids[0]);
        assertEquals(4L, ids[1]);
        assertEquals(5L, ids[2]);
    }

    /**
     * The method arranges region {@code cap} with spill off and two resident pages.
     * The first two fat appends succeed.
     * The third append fails, and the message contains {@code resident-pages}.
     * The status contains {@code spilled=0}, {@code resident=2}, and {@code seq=2}.
     * Messages {@code 1} and {@code 2} start with {@code a} and {@code b}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void spillOffStopsAtTheResidentCap() throws IOException {
        Seat seat = seat("cap", false, 2, true);
        assertTrue(seat.append(Role.USER, 8, fat('a')).isSuccess());
        assertTrue(seat.append(Role.USER, 8, fat('b')).isSuccess());
        OperationResult blocked = seat.append(Role.USER, 8, fat('c'));
        assertFalse(blocked.isSuccess());
        assertTrue(blocked.getMessage().contains("resident-pages"));
        String line = seat.engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("spilled=0"));
        assertTrue(line.contains("resident=2"));
        assertTrue(line.contains("seq=2"));
        assertTrue(seat.engine.readMessage(seat.region, 1L).getText().startsWith("a"));
        assertTrue(seat.engine.readMessage(seat.region, 2L).getText().startsWith("b"));
    }

    /**
     * The method arranges a closed spill of four messages in region {@code reopen}.
     * The method reopens the region and calls {@link RouxEngine#openRegion}.
     * The kind is {@link OpenResult#RESTORED}.
     * The message count is {@code 4}.
     * The region uuid matches the uuid saved before the reopen.
     * The manifest bytes are unchanged.
     * Message {@code 1} is {@code SPILLED} and starts with {@code b}.
     * Message {@code 4} is {@code HOT} and starts with {@code e}.
     * The status contains {@code hot=3} and {@code seq=4}.
     * The pack file length is four pages.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aClosedPackReopensOnTheSameIds() throws IOException {
        Seat seat = spilled("reopen");
        byte[] before = Files.readAllBytes(manifest(seat));
        UUID uuid = load(seat).getRegionUuid();
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(4, opened.getMessageCount());
        assertEquals(uuid, opened.getRegionUuid());
        assertArrayEquals(before, Files.readAllBytes(manifest(seat)));
        assertEquals("SPILLED", engine.inspect(seat.region, 1L).getResidency());
        assertEquals("HOT", engine.inspect(seat.region, 4L).getResidency());
        assertTrue(engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertTrue(engine.readMessage(seat.region, 4L).getText().startsWith("e"));
        String line = engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("hot=3"));
        assertTrue(line.contains("seq=4"));
        assertEquals(4L * PAGE, Files.size(pack(seat)));
    }

    /**
     * The method arranges a closed spill in region {@code boot} and a new engine that has not opened it.
     * {@link RouxEngine#readMessage} returns null for message {@code 1}.
     * The pack bytes are unchanged.
     * Opening the region returns {@link OpenResult#RESTORED} with message count {@code 4}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void theEngineDoesNotScanUntilThatRegionOpens() throws IOException {
        Seat seat = spilled("boot");
        byte[] before = Files.readAllBytes(pack(seat));
        RouxEngine engine = reopen(seat);
        assertNull(engine.readMessage(seat.region, 1L));
        assertArrayEquals(before, Files.readAllBytes(pack(seat)));
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(4, opened.getMessageCount());
    }

    /**
     * The method deletes the manifest for a closed spill in region {@code noman} and opens the region again.
     * The kind is {@link OpenResult#RESTORED} with message count {@code 4}.
     * The region uuid differs from the uuid saved before the delete.
     * Messages {@code 1} and {@code 4} start with {@code b} and {@code e}.
     * The new manifest has page count {@code 4}, hot page {@code 3}, the new uuid, and the region id as owner.
     * The status contains {@code hot=3} and {@code seq=4}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aMissingManifestWithAValidSlotIsWalked() throws IOException {
        Seat seat = spilled("noman");
        UUID oldUuid = load(seat).getRegionUuid();
        Files.delete(manifest(seat));
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(4, opened.getMessageCount());
        assertNotEquals(oldUuid, opened.getRegionUuid());
        assertTrue(engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertTrue(engine.readMessage(seat.region, 4L).getText().startsWith("e"));
        RegionManifest man = load(seat);
        assertEquals(4, man.getPageCount());
        assertEquals(3, man.getHotPageId());
        assertEquals(opened.getRegionUuid(), man.getRegionUuid());
        assertEquals(seat.region, man.getOwner());
        String line = engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("hot=3"));
        assertTrue(line.contains("seq=4"));
    }

    /**
     * The method deletes the manifest and zeroes the first four pack bytes in region {@code noslot}.
     * The open kind is refused.
     * The reason contains {@code incomplete or corrupt}.
     * The pack file remains.
     * The manifest file is absent.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aMissingManifestWithNoSlotRefuses() throws IOException {
        Seat seat = spilled("noslot");
        Files.delete(manifest(seat));
        zero(pack(seat), 0, 4);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertTrue(opened.getReason().contains("incomplete or corrupt"));
        assertTrue(Files.isRegularFile(pack(seat)));
        assertFalse(Files.isRegularFile(manifest(seat)));
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method stores a manifest that claims page count {@code 2} and hot page {@code 1}, then opens region {@code longer}.
     * The open kind is {@link OpenResult#RESTORED} with message count {@code 4}.
     * Messages {@code 3} and {@code 4} start with {@code d} and {@code e}.
     * The repaired manifest has page count {@code 4} and hot page {@code 3}.
     * A new fat message starting with {@code z} appends as message {@code 5}.
     * Messages {@code 1}, {@code 3}, and {@code 4} still start with {@code b}, {@code d}, and {@code e}.
     * The status contains {@code seq=5} and {@code hot=4}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aLongerWalkIsTakenAndTheNextAppendKeepsTheTail() throws IOException {
        Seat seat = spilled("longer");
        RegionManifest man = load(seat);
        man.setPageCount(2);
        man.setHotPageId(1);
        store(seat, man);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(4, opened.getMessageCount());
        assertTrue(engine.readMessage(seat.region, 3L).getText().startsWith("d"));
        assertTrue(engine.readMessage(seat.region, 4L).getText().startsWith("e"));
        RegionManifest repaired = load(seat);
        assertEquals(4, repaired.getPageCount());
        assertEquals(3, repaired.getHotPageId());
        assertTrue(engine.appendMessage(seat.region, Role.USER, 1710000000000L, 8, fat('z')).isSuccess());
        assertTrue(engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertTrue(engine.readMessage(seat.region, 3L).getText().startsWith("d"));
        assertTrue(engine.readMessage(seat.region, 4L).getText().startsWith("e"));
        assertTrue(engine.readMessage(seat.region, 5L).getText().startsWith("z"));
        String line = engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("seq=5"));
        assertTrue(line.contains("hot=4"));
    }

    /**
     * The method stores a manifest that claims page count {@code 6} and hot page {@code 0} for region {@code shorter}.
     * The open kind is refused.
     * The reason contains {@code incomplete or corrupt}.
     * The manifest bytes stay equal to the bytes written before the open.
     * Those bytes differ from the manifest taken before the rewrite.
     * Both the pack and the manifest remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aShorterWalkRefusesAndLeavesTheFiles() throws IOException {
        Seat seat = spilled("shorter");
        byte[] before = Files.readAllBytes(manifest(seat));
        RegionManifest man = load(seat);
        man.setPageCount(6);
        man.setHotPageId(0);
        store(seat, man);
        byte[] promised = Files.readAllBytes(manifest(seat));
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertTrue(opened.getReason().contains("incomplete or corrupt"));
        assertArrayEquals(promised, Files.readAllBytes(manifest(seat)));
        assertFalse(Arrays.equals(before, promised));
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method sets the hot page id equal to the page count in region {@code hotid}.
     * The open kind is refused.
     * The manifest bytes are unchanged.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aNonsenseHotIdRefuses() throws IOException {
        Seat seat = spilled("hotid");
        RegionManifest man = load(seat);
        man.setHotPageId(man.getPageCount());
        store(seat, man);
        byte[] before = Files.readAllBytes(manifest(seat));
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertArrayEquals(before, Files.readAllBytes(manifest(seat)));
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method zeroes the first four pack bytes and stores page count {@code 0} for region {@code zerocount}.
     * The open kind is refused.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aZeroCountRefuses() throws IOException {
        Seat seat = spilled("zerocount");
        zero(pack(seat), 0, 4);
        RegionManifest man = load(seat);
        man.setPageCount(0);
        man.setHotPageId(0);
        store(seat, man);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method writes version byte {@code 1} on the third page of region {@code version}.
     * The open kind is refused.
     * The manifest bytes are unchanged.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aVersionOtherThanTwoRefuses() throws IOException {
        Seat seat = spilled("version");
        poke(pack(seat), 2 * PAGE + 4, 1);
        byte[] before = Files.readAllBytes(manifest(seat));
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertArrayEquals(before, Files.readAllBytes(manifest(seat)));
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method writes page id {@code 9} into the second page of region {@code pageid}.
     * The open kind is refused.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aPageIdOtherThanTheSlotRefuses() throws IOException {
        Seat seat = spilled("pageid");
        pokeInt(pack(seat), PAGE + 8, 9);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method truncates the pack to three pages plus {@code 32} bytes in region {@code shorthead}.
     * The open kind is refused.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aShortHeaderEndsTheWalk() throws IOException {
        Seat seat = spilled("shorthead");
        truncate(pack(seat), 3L * PAGE + 32L);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method zeroes four bytes at the second page of region {@code hole} and leaves the manifest count.
     * The open kind is refused.
     * The manifest bytes are unchanged.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aHoleRefusesWhenTheManifestStillCountsIt() throws IOException {
        Seat seat = spilled("hole");
        zero(pack(seat), PAGE, 4);
        byte[] before = Files.readAllBytes(manifest(seat));
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertArrayEquals(before, Files.readAllBytes(manifest(seat)));
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method zeroes four bytes at the second page and stores page count {@code 1} for region {@code skip}.
     * The open kind is {@link OpenResult#RESTORED} with message count {@code 1}.
     * Message {@code 1} starts with {@code b}.
     * Messages {@code 2} and {@code 3} read as null.
     * The status contains {@code hot=0} and {@code seq=1}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aHoleEndsTheWalkAndDoesNotSkip() throws IOException {
        Seat seat = spilled("skip");
        zero(pack(seat), PAGE, 4);
        RegionManifest man = load(seat);
        man.setPageCount(1);
        man.setHotPageId(0);
        store(seat, man);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(1, opened.getMessageCount());
        assertTrue(engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertNull(engine.readMessage(seat.region, 2L));
        assertNull(engine.readMessage(seat.region, 3L));
        String line = engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("hot=0"));
        assertTrue(line.contains("seq=1"));
    }

    /**
     * The method deletes the pack for region {@code nopack} and opens the region.
     * The open kind is refused.
     * The reason contains {@code incomplete or corrupt}.
     * The pack file is absent.
     * The manifest file remains.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aManifestWithoutAPackRefuses() throws IOException {
        Seat seat = spilled("nopack");
        Files.delete(pack(seat));
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertTrue(opened.getReason().contains("incomplete or corrupt"));
        assertFalse(Files.isRegularFile(pack(seat)));
        assertTrue(Files.isRegularFile(manifest(seat)));
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method reopens region {@code restoreoff} with restore disabled.
     * The open kind is refused.
     * The reason contains {@code restore disabled}.
     * The pack bytes are unchanged.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void restoreOffRefusesALeftover() throws IOException {
        Seat seat = spilled("restoreoff");
        byte[] before = Files.readAllBytes(pack(seat));
        RouxEngine engine = reopen(seat, PAGE, false, true, "commit");
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertTrue(opened.getReason().contains("restore disabled"));
        assertArrayEquals(before, Files.readAllBytes(pack(seat)));
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method reopens region {@code pitch} with page size {@code 1024}.
     * The open kind is refused.
     * The reason contains {@code page-bytes mismatch}.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void pageBytesDisagreeRefuses() throws IOException {
        Seat seat = spilled("pitch");
        RouxEngine engine = reopen(seat, 1024, true, true, "commit");
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertTrue(opened.getReason().contains("page-bytes mismatch"));
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method stores format version {@code 1} for region {@code format}.
     * The open kind is refused.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aManifestFormatVersionDisagrees() throws IOException {
        Seat seat = spilled("format");
        RegionManifest man = load(seat);
        man.setFormatVersion(1);
        store(seat, man);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method stores owner {@code stranger} for region {@code owner}.
     * The open kind is refused.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void anOwnerDisagrees() throws IOException {
        Seat seat = spilled("owner");
        RegionManifest man = load(seat);
        man.setOwner("stranger");
        store(seat, man);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method stores an empty owner for region {@code emptyowner}.
     * The open kind is {@link OpenResult#RESTORED} with message count {@code 4}.
     * Message {@code 4} starts with {@code e}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void anEmptyOwnerReopens() throws IOException {
        Seat seat = spilled("emptyowner");
        RegionManifest man = load(seat);
        man.setOwner("");
        store(seat, man);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(4, opened.getMessageCount());
        assertTrue(engine.readMessage(seat.region, 4L).getText().startsWith("e"));
    }

    /**
     * The method pokes byte {@code 0x5A} at offset {@code 80} of the manifest for region {@code mancrc}.
     * The open kind is refused.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aBadManifestCrcRefuses() throws IOException {
        Seat seat = spilled("mancrc");
        poke(manifest(seat), 80, 0x5A);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method pokes {@code Q} at the first manifest byte of region {@code manmagic}.
     * The open kind is refused.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aBadManifestMagicRefuses() throws IOException {
        Seat seat = spilled("manmagic");
        poke(manifest(seat), 0, (int) 'Q');
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method truncates the manifest to {@code 40} bytes for region {@code shortman}.
     * The open kind is refused.
     * The pack file remains.
     * The manifest file remains.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aShortManifestRefuses() throws IOException {
        Seat seat = spilled("shortman");
        truncate(manifest(seat), 40L);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        assertTrue(Files.isRegularFile(pack(seat)));
        assertTrue(Files.isRegularFile(manifest(seat)));
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method pokes byte {@code 9} at offset {@code 4} of the manifest for region {@code meta}.
     * The open kind is refused.
     * Both files remain.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aManifestMetaVersionRefuses() throws IOException {
        Seat seat = spilled("meta");
        poke(manifest(seat), 4, 9);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertRefused(opened);
        filesRemain(seat);
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method pokes byte {@code 0x11} into the fourth page frame of region {@code framecrc}.
     * The open kind is {@link OpenResult#RESTORED} with message count {@code 3}.
     * Messages {@code 1} and {@code 3} start with {@code b} and {@code d}.
     * Message {@code 4} reads as null.
     * Both files remain.
     * The status contains {@code seq=3}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aBadFrameCrcOpensShort() throws IOException {
        Seat seat = spilled("framecrc");
        poke(pack(seat), 3 * PAGE + 64 + 32, 0x11);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(3, opened.getMessageCount());
        assertTrue(engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertTrue(engine.readMessage(seat.region, 3L).getText().startsWith("d"));
        assertNull(engine.readMessage(seat.region, 4L));
        filesRemain(seat);
        String line = engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("seq=3"));
    }

    /**
     * The method writes frame length {@code 4} on the fourth page of region {@code framelen}.
     * The open kind is {@link OpenResult#RESTORED} with message count {@code 3}.
     * Message {@code 3} starts with {@code d}.
     * Message {@code 4} reads as null.
     * Both files remain.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aBadFrameLengthOpensShort() throws IOException {
        Seat seat = spilled("framelen");
        pokeInt(pack(seat), 3 * PAGE + 64, 4);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(3, opened.getMessageCount());
        assertTrue(engine.readMessage(seat.region, 3L).getText().startsWith("d"));
        assertNull(engine.readMessage(seat.region, 4L));
        filesRemain(seat);
    }

    /**
     * The method writes sequence {@code 9} into the third page of region {@code seqgap}.
     * The open kind is {@link OpenResult#RESTORED} with message count {@code 2}.
     * Messages {@code 1} and {@code 2} start with {@code b} and {@code c}.
     * Messages {@code 3} and {@code 4} read as null.
     * Both files remain.
     * The status contains {@code seq=2}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aSeqGapOpensShortAndDoesNotRenumber() throws IOException {
        Seat seat = spilled("seqgap");
        pokeInt(pack(seat), 2 * PAGE + 64 + 8, 9);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(2, opened.getMessageCount());
        assertTrue(engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertTrue(engine.readMessage(seat.region, 2L).getText().startsWith("c"));
        assertNull(engine.readMessage(seat.region, 3L));
        assertNull(engine.readMessage(seat.region, 4L));
        filesRemain(seat);
        String line = engine.memoryStatusLine(seat.region);
        assertTrue(line.contains("seq=2"));
    }

    /**
     * The method pokes byte {@code 0x3C} at offset {@code 24} of the pack for region {@code pagecrc}.
     * The open kind is {@link OpenResult#RESTORED} with message count {@code 4}.
     * Messages {@code 1} and {@code 4} start with {@code b} and {@code e}.
     * The manifest bytes are unchanged.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aPageCrcStaysOutOfTheScan() throws IOException {
        Seat seat = spilled("pagecrc");
        byte[] manBefore = Files.readAllBytes(manifest(seat));
        poke(pack(seat), 24, 0x3C);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(4, opened.getMessageCount());
        assertTrue(engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertTrue(engine.readMessage(seat.region, 4L).getText().startsWith("e"));
        assertArrayEquals(manBefore, Files.readAllBytes(manifest(seat)));
    }

    /**
     * The method arranges region {@code filelen} with alignment {@code 64} and one message {@code solo}.
     * The method closes the region and checks that the pack is shorter than one page.
     * Dividing that size by the page size yields {@code 0}.
     * Reopening returns {@link OpenResult#RESTORED} with message count {@code 1}.
     * Message {@code 1} equals {@code solo}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void fileLengthIsNotThePageCount() throws IOException {
        Seat seat = owned("filelen", true, "commit", 64);
        assertTrue(seat.append(Role.USER, 4, "solo").isSuccess());
        assertTrue(seat.engine.persistClose(seat.region).isSuccess());
        long size = Files.size(pack(seat));
        assertTrue(size < PAGE);
        assertEquals(0L, size / PAGE);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(1, opened.getMessageCount());
        assertEquals("solo", engine.readMessage(seat.region, 1L).getText());
    }

    /**
     * The method deletes the manifest and creates a directory named {@code manifest.bin.tmp} in region {@code failwrite}.
     * The open kind is {@link OpenResult#RESTORED} with message count {@code 4}.
     * Messages {@code 1} and {@code 4} start with {@code b} and {@code e}.
     * The manifest file is absent after the open.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void aFailedManifestWriteLeavesTheRegionOpen() throws IOException {
        Seat seat = spilled("failwrite");
        Files.delete(manifest(seat));
        Files.createDirectory(regionDir(seat).resolve("manifest.bin.tmp"));
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.RESTORED, opened.getKind());
        assertEquals(4, opened.getMessageCount());
        assertTrue(engine.readMessage(seat.region, 1L).getText().startsWith("b"));
        assertTrue(engine.readMessage(seat.region, 4L).getText().startsWith("e"));
        assertFalse(Files.isRegularFile(manifest(seat)));
    }

    /**
     * The method arranges region {@code fresh} with no messages and reopens it.
     * The open kind is {@link OpenResult#NEW}.
     * The open is ok.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void bothFilesAbsentAllocateANewRegion() throws IOException {
        Seat seat = seat("fresh", true, 2, true);
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.NEW, opened.getKind());
        assertTrue(opened.isOk());
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method arranges region {@code emptyid} and calls {@link RouxEngine#openRegion} with an empty id and with null.
     * Both opens are refused.
     * Both reasons contain {@code invalid username}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void anEmptyIdIsRefused() throws IOException {
        Seat seat = seat("emptyid", true, 2, false);
        OpenResult blank = seat.engine.openRegion("");
        OpenResult missing = seat.engine.openRegion(null);
        assertRefused(blank);
        assertRefused(missing);
        assertTrue(blank.getReason().contains("invalid username"));
        assertTrue(missing.getReason().contains("invalid username"));
    }

    /**
     * The method opens region {@code live} twice on the same engine.
     * The first kind is {@link OpenResult#NEW}.
     * The second open is refused.
     * The second reason contains {@code session already open}.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because arranging the region can throw that failure.
     */
    @Test
    void aLiveRegionIsRefused() throws IOException {
        Seat seat = seat("live", true, 2, false);
        OpenResult first = seat.engine.openRegion(seat.region);
        OpenResult second = seat.engine.openRegion(seat.region);
        assertEquals(OpenResult.NEW, first.getKind());
        assertRefused(second);
        assertTrue(second.getReason().contains("session already open"));
    }

    /**
     * The method arranges region {@code drop} with persist off and four fat messages.
     * The pack file exists before the close.
     * {@link RouxEngine#persistClose} succeeds.
     * The region directory is gone.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void persistOffDeletesTheDirectory() throws IOException {
        Seat seat = owned("drop", false, "commit", 4096);
        for (int i = 1; i <= 4; i++) {
            assertTrue(seat.append(Role.USER, 8, fat((char) ('a' + i))).isSuccess());
        }
        assertTrue(Files.isRegularFile(pack(seat)));
        assertTrue(seat.engine.persistClose(seat.region).isSuccess());
        assertFalse(Files.exists(regionDir(seat)));
    }

    /**
     * The method reopens a closed spill in region {@code frozen} with durability {@code hot-only}.
     * The open kind is {@link OpenResult#NEW} with message count {@code 0}.
     * Message {@code 1} reads as null.
     * The pack bytes are unchanged.
     * The manifest bytes are unchanged.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void hotOnlyLeavesAFrozenPackUntouched() throws IOException {
        Seat seat = spilled("frozen");
        byte[] before = Files.readAllBytes(pack(seat));
        byte[] manBefore = Files.readAllBytes(manifest(seat));
        RouxEngine engine = reopen(seat, PAGE, true, true, "hot-only");
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.NEW, opened.getKind());
        assertEquals(0, opened.getMessageCount());
        assertNull(engine.readMessage(seat.region, 1L));
        assertArrayEquals(before, Files.readAllBytes(pack(seat)));
        assertArrayEquals(manBefore, Files.readAllBytes(manifest(seat)));
    }

    /**
     * The method arranges region {@code unforced} with durability {@code hot-only} and one message {@code ram}.
     * The message reads back before the close.
     * {@link RouxEngine#persistClose} succeeds.
     * The pack file is absent.
     * A later open returns {@link OpenResult#NEW}.
     * Message {@code 1} reads as null.
     *
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the file calls can throw that failure.
     */
    @Test
    void hotOnlyLosesTheUnforcedPage() throws IOException {
        Seat seat = owned("unforced", true, "hot-only", 4096);
        assertTrue(seat.append(Role.USER, 4, "ram").isSuccess());
        assertTrue(seat.engine.readMessage(seat.region, 1L).getText().startsWith("ram"));
        assertTrue(seat.engine.persistClose(seat.region).isSuccess());
        assertFalse(Files.isRegularFile(pack(seat)));
        RouxEngine engine = reopen(seat);
        OpenResult opened = engine.openRegion(seat.region);
        assertEquals(OpenResult.NEW, opened.getKind());
        assertNull(engine.readMessage(seat.region, 1L));
    }

    /**
     * The method builds a spill region, appends four fat user messages, and closes it.
     * Each append must succeed.
     *
     * @param name The name is the suffix used in the temporary directory.
     * @return The method returns the closed seat.
     * @throws IOException The method declares {@link IOException} because creating the directory can throw that failure.
     */
    private Seat spilled(String name) throws IOException {
        Seat seat = seat(name, true, 2, true);
        for (int i = 1; i <= 4; i++) {
            assertTrue(seat.append(Role.USER, 8, fat((char) ('a' + i))).isSuccess());
        }
        assertTrue(seat.engine.persistClose(seat.region).isSuccess());
        return seat;
    }

    /**
     * The method reopens {@code seat} with the test page size, restore on, persist on, and durability {@code commit}.
     *
     * @param seat The seat holds the directory and the region id.
     * @return The method returns the new engine.
     */
    private RouxEngine reopen(Seat seat) {
        return reopen(seat, PAGE, true, true, "commit");
    }

    /**
     * The method builds a new engine over the same directory.
     * Resident pages are {@code 8}.
     * Spill is enabled.
     * The method adopts that engine onto {@code seat}.
     *
     * @param seat The seat holds the directory and the region id.
     * @param pageBytes The count is the page size in bytes.
     * @param restore The flag turns region restore on or off.
     * @param persist The flag turns region persist on or off.
     * @param durability The word is the durability mode.
     * @return The method returns the new engine.
     */
    private RouxEngine reopen(Seat seat, int pageBytes, boolean restore, boolean persist, String durability) {
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(8);
        properties.setDurability(durability);
        properties.setPageBytes(pageBytes);
        properties.getSpill().setEnabled(true);
        properties.getSpill().setDir(seat.dir.toString());
        properties.getSession().setRestore(restore);
        properties.getSession().setPersist(persist);
        RouxCounters counters = new RouxCounters();
        RegionPack pack = new RegionPack(properties);
        RouxEngine engine = new RouxEngine(properties, pack, counters, new CryptoSink(properties), new RouxIdGenerator());
        seat.adopt(engine, pack);
        return engine;
    }

    /**
     * The method builds a region with two resident pages, the given durability, and the given alignment.
     * Spill is enabled and restore is on.
     *
     * @param name The name is the suffix used in the temporary directory.
     * @param persist The flag turns persist on or off.
     * @param durability The word is the durability mode.
     * @param align The count is the spill alignment in bytes.
     * @return The method returns the new seat.
     * @throws IOException The method declares {@link IOException} because creating the directory can throw that failure.
     */
    private Seat owned(String name, boolean persist, String durability, int align) throws IOException {
        Path dir = Files.createTempDirectory("roux-f169-" + name);
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(2);
        properties.setDurability(durability);
        properties.setPageBytes(PAGE);
        properties.getSpill().setEnabled(true);
        properties.getSpill().setDir(dir.toString());
        properties.getSpill().setAlignBytes(align);
        properties.getSession().setPersist(persist);
        properties.getSession().setRestore(true);
        RouxCounters counters = new RouxCounters();
        RouxEngine engine = new RouxEngine(properties, new RegionPack(properties), counters, new CryptoSink(properties),
                new RouxIdGenerator());
        Seat held = new Seat(engine, counters, "f169-" + name, dir);
        seats.add(held);
        return held;
    }

    /**
     * The method resolves the region directory under the seat directory.
     *
     * @param seat The seat holds the directory and the region id.
     * @return The method returns that directory.
     */
    private static Path regionDir(Seat seat) {
        return seat.dir.resolve(seat.region);
    }

    /**
     * The method resolves {@code pages.bin} inside the region directory.
     *
     * @param seat The seat holds the directory and the region id.
     * @return The method returns that pack path.
     */
    private static Path pack(Seat seat) {
        return regionDir(seat).resolve("pages.bin");
    }

    /**
     * The method resolves the manifest file inside the region directory.
     *
     * @param seat The seat holds the directory and the region id.
     * @return The method returns that manifest path.
     */
    private static Path manifest(Seat seat) {
        return regionDir(seat).resolve(RegionManifest.FILE_NAME);
    }

    /**
     * The method reads the manifest from the region directory.
     *
     * @param seat The seat holds the directory and the region id.
     * @return The method returns that manifest.
     * @throws IOException The method declares {@link IOException} because {@link RegionManifest#read} declares that failure.
     */
    private static RegionManifest load(Seat seat) throws IOException {
        return RegionManifest.read(regionDir(seat));
    }

    /**
     * The method writes {@code man} into the region directory.
     *
     * @param seat The seat holds the directory and the region id.
     * @param man The manifest is the record to write.
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because {@link RegionManifest#write} declares that failure.
     */
    private static void store(Seat seat, RegionManifest man) throws IOException {
        RegionManifest.write(regionDir(seat), man);
    }

    /**
     * The method asserts that the pack file and the manifest file both exist.
     *
     * @param seat The seat holds the directory and the region id.
     * @return The method returns nothing.
     */
    private static void filesRemain(Seat seat) {
        assertTrue(Files.isRegularFile(pack(seat)));
        assertTrue(Files.isRegularFile(manifest(seat)));
    }

    /**
     * The method asserts that {@code opened} is refused.
     * {@link OpenResult#isOk} is false.
     *
     * @param opened The result is the open under test.
     * @return The method returns nothing.
     */
    private static void assertRefused(OpenResult opened) {
        assertEquals(OpenResult.REFUSED, opened.getKind());
        assertFalse(opened.isOk());
    }

    /**
     * The method replaces one byte of {@code file}.
     *
     * @param file The file is the pack or the manifest.
     * @param offset The offset is the byte index to replace.
     * @param value The value is the new byte.
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because reading or writing the file can throw that failure.
     */
    private static void poke(Path file, int offset, int value) throws IOException {
        byte[] raw = Files.readAllBytes(file);
        raw[offset] = (byte) value;
        Files.write(file, raw);
    }

    /**
     * The method writes {@code value} as four little-endian bytes.
     *
     * @param file The file is the pack or the manifest.
     * @param offset The offset is the first byte index.
     * @param value The value is the integer to write.
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because reading or writing the file can throw that failure.
     */
    private static void pokeInt(Path file, int offset, int value) throws IOException {
        byte[] raw = Files.readAllBytes(file);
        raw[offset] = (byte) value;
        raw[offset + 1] = (byte) (value >>> 8);
        raw[offset + 2] = (byte) (value >>> 16);
        raw[offset + 3] = (byte) (value >>> 24);
        Files.write(file, raw);
    }

    /**
     * The method writes {@code length} zero bytes at {@code offset}.
     *
     * @param file The file is the pack or the manifest.
     * @param offset The offset is the first byte index.
     * @param length The count is the number of bytes to clear.
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because reading or writing the file can throw that failure.
     */
    private static void zero(Path file, int offset, int length) throws IOException {
        byte[] raw = Files.readAllBytes(file);
        for (int i = 0; i < length; i++) {
            raw[offset + i] = 0;
        }
        Files.write(file, raw);
    }

    /**
     * The method shortens {@code file} to {@code size} bytes.
     *
     * @param file The file is the pack or the manifest.
     * @param size The size is the new length in bytes.
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because opening or truncating the file can throw that failure.
     */
    private static void truncate(Path file, long size) throws IOException {
        FileChannel channel = FileChannel.open(file, StandardOpenOption.WRITE);
        try {
            channel.truncate(size);
        } finally {
            channel.close();
        }
    }

    /**
     * The method builds an engine with durability {@code commit} and the given spill flag.
     * A true {@code fatPage} sets the page size to {@code 512}.
     * The region id starts with {@code f169-}.
     *
     * @param name The name is the suffix used in the temporary directory.
     * @param spill The flag turns spill on or off.
     * @param resident The count is the resident page cap.
     * @param fatPage The flag selects a {@code 512} byte page when true.
     * @return The method returns the new seat.
     * @throws IOException The method declares {@link IOException} because creating the directory can throw that failure.
     */
    private Seat seat(String name, boolean spill, int resident, boolean fatPage) throws IOException {
        Path dir = Files.createTempDirectory("roux-f169-" + name);
        RouxProperties properties = new RouxProperties();
        properties.setResidentPages(resident);
        properties.setDurability("commit");
        if (fatPage) {
            properties.setPageBytes(PAGE);
        }
        properties.getSpill().setEnabled(spill);
        properties.getSpill().setDir(dir.toString());
        RouxCounters counters = new RouxCounters();
        RouxEngine engine = new RouxEngine(properties, new RegionPack(properties), counters, new CryptoSink(properties),
                new RouxIdGenerator());
        Seat held = new Seat(engine, counters, "f169-" + name, dir);
        seats.add(held);
        return held;
    }

    /**
     * The method builds a string of length {@code 280}.
     * The first character is {@code mark}.
     * The remaining characters are {@code x}.
     *
     * @param mark The character is the first character of the text.
     * @return The method returns that string.
     */
    private static String fat(char mark) {
        StringBuilder sb = new StringBuilder(FAT);
        sb.append(mark);
        while (sb.length() < FAT) {
            sb.append('x');
        }
        return sb.toString();
    }

    // @aa-sdd PK-1108 docs/specs/common/formal-plan.spec.md FINESSE:f-169
    /**
     * The class holds one {@link RouxEngine}, its counters, the region id, and the directory that engine spills into.
     * A later engine can be adopted for a reopen.
     * Closing the seat persists both engines and deletes the directory.
     */
    // @aa-sdd GK-1931 aetheris-pre-plan-f-169.001.md
    private static final class Seat {

        private final RouxEngine engine;
        private final RouxCounters counters;
        private final String region;
        private final Path dir;
        private RouxEngine extraEngine;
        private RegionPack extraPack;

        /**
         * The constructor stores the engine, the counters, the region id, and the directory.
         *
         * @param engine The engine is the first region engine.
         * @param counters The counters are the telemetry object for that engine.
         * @param region The region is the region id.
         * @param dir The directory is the spill directory.
         */
        private Seat(RouxEngine engine, RouxCounters counters, String region, Path dir) {
            this.engine = engine;
            this.counters = counters;
            this.region = region;
            this.dir = dir;
        }

        /**
         * The method keeps a second engine and its pack for a later close.
         *
         * @param engine The engine is the reopened engine.
         * @param pack The pack is the region pack for that engine.
         * @return The method returns nothing.
         */
        private void adopt(RouxEngine engine, RegionPack pack) {
            this.extraEngine = engine;
            this.extraPack = pack;
        }

        /**
         * The method appends one message at timestamp {@code 1710000000000}.
         *
         * @param role The role is the message role byte.
         * @param tokens The count is the token count stored with the message.
         * @param text The text is the message body.
         * @return The method returns the append result.
         */
        private OperationResult append(byte role, int tokens, String text) {
            return engine.appendMessage(region, role, 1710000000000L, tokens, text);
        }

        /**
         * The method persists the adopted engine when one is present.
         * The method closes and unlocks the adopted pack when one is present.
         * The method persists the first engine.
         * The method deletes the directory.
         *
         * @return The method returns nothing.
         * @throws IOException The method declares {@link IOException} because deleting the directory can throw that failure.
         */
        private void close() throws IOException {
            if (extraEngine != null) {
                extraEngine.persistClose(region);
            }
            if (extraPack != null) {
                extraPack.closeChannel(region);
                extraPack.unlock(region);
            }
            engine.persistClose(region);
            deleteTree(dir);
        }
    }

    /**
     * The method deletes {@code root} and every path under it, children first.
     * A null root is left alone.
     * A path that does not exist is left alone.
     *
     * @param root The root is the directory to delete.
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because the deletes can throw that failure.
     */
    private static void deleteTree(Path root) throws IOException {
        if (root == null || !Files.exists(root)) {
            return;
        }
        ArrayList<Path> paths = new ArrayList<Path>();
        collect(root, paths);
        paths.sort(Comparator.reverseOrder());
        for (int i = 0; i < paths.size(); i++) {
            Files.deleteIfExists(paths.get(i));
        }
    }

    /**
     * The method adds {@code dir} to {@code paths} and then adds each child.
     * A path that is not a directory adds no children.
     *
     * @param dir The directory is the path to record.
     * @param paths The list receives {@code dir} and its children.
     * @return The method returns nothing.
     * @throws IOException The method declares {@link IOException} because reading the directory can throw that failure.
     */
    private static void collect(Path dir, ArrayList<Path> paths) throws IOException {
        paths.add(dir);
        if (!Files.isDirectory(dir)) {
            return;
        }
        DirectoryStream<Path> stream = Files.newDirectoryStream(dir);
        try {
            java.util.Iterator<Path> it = stream.iterator();
            while (it.hasNext()) {
                collect(it.next(), paths);
            }
        } finally {
            stream.close();
        }
    }
}
