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

package org.aetheris.swing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import org.aetheris.swing.AetherisNotebookPanel.HideSeat;
import org.aetheris.vault.CellRecord;
import org.junit.jupiter.api.Test;

// @aa-sdd GK-1080 aetheris-plan-f-213.001.md FINESSE:f-213
/**
 * The class checks Hide Collapsed on {@link AetherisNotebookPanel}.
 * Hide Collapsed takes folded cards off the canvas.
 * The test does not open a window.
 *
 * @author clayl (2026-10-29)
 */
// @aa-sdd GK-1585 aetheris-stage-2-finessing.001.md
// @aa-sdd GK-2470 rust-aetheris-hide-collapsed-java-slice.draft.001.md
// @aa-sdd GK-2471 rust-aetheris-hide-seat-java-slice.draft.001.md
// @aa-sdd GK-2817 aetheris-plan-f-213.001.md
// @aa-sdd GK-2819 aetheris-plan-f-213.001.md
// @aa-sdd GK-2821 aetheris-plan-f-213.001.md
class HideCollapsedTest {

    /**
     * The method builds one {@link CellRecord}.
     * The method stores {@code id} on that record.
     * The method stores {@code collapsed} on that record.
     *
     * @param id The id is the string stored on the record.
     * @param collapsed The flag is true when the card is folded.
     * @return The method returns the new record.
     */
    private static CellRecord cell(String id, boolean collapsed) {
        CellRecord cell = new CellRecord();
        cell.id = id;
        cell.collapsed = collapsed;
        return cell;
    }

    /**
     * The method arranges a solo card, a group {@code g} with two column cards and a merge card, and a later card.
     * The merge text joins {@code gemma3:12b} and {@code qwen3.5:9b}.
     * The method calls {@link AetherisNotebookPanel#transferRows}.
     * The role list and the text list each hold six entries.
     * The first pair is the user text {@code solo} and the assistant text {@code back}.
     * The next user text is {@code ask-a} joined to {@code ask-b} by a newline.
     * The next assistant text is the merge text.
     * The last pair is the user text {@code next} and the assistant text {@code after}.
     * The merge text contains {@code JOIN gemma3:12b} and {@code JOIN qwen3.5:9b}.
     *
     * @return The method returns nothing.
     */
    @Test
    void aMergeGroupLeavesAsOnePair() {
        ArrayList<CellRecord> ordered = new ArrayList<CellRecord>();
        CellRecord solo = cell("s", false);
        solo.userText = "solo";
        solo.assistantText = "back";
        ordered.add(solo);
        CellRecord first = cell("a", false);
        first.groupId = "g";
        first.columnPosition = 1;
        first.userText = "ask-a";
        first.assistantText = "reply-a";
        first.executionTarget = "ollama/gemma3:12b";
        ordered.add(first);
        CellRecord second = cell("b", false);
        second.groupId = "g";
        second.columnPosition = 2;
        second.userText = "ask-b";
        second.assistantText = "reply-b";
        second.executionTarget = "ollama/qwen3.5:9b";
        ordered.add(second);
        CellRecord merge = cell("m", false);
        merge.groupId = "g";
        merge.columnPosition = 0;
        merge.userText = "";
        merge.assistantText = "JOIN gemma3:12b\nreply-a\n\nJOIN qwen3.5:9b\nreply-b";
        ordered.add(merge);
        CellRecord later = cell("z", false);
        later.userText = "next";
        later.assistantText = "after";
        ordered.add(later);
        ArrayList<String> roles = new ArrayList<String>();
        ArrayList<String> texts = new ArrayList<String>();
        AetherisNotebookPanel.transferRows(ordered, roles, texts);
        assertEquals(6, roles.size());
        assertEquals(6, texts.size());
        assertEquals("user", roles.get(0));
        assertEquals("solo", texts.get(0));
        assertEquals("assistant", roles.get(1));
        assertEquals("back", texts.get(1));
        assertEquals("user", roles.get(2));
        assertEquals("ask-a\nask-b", texts.get(2));
        assertEquals("assistant", roles.get(3));
        assertEquals("JOIN gemma3:12b\nreply-a\n\nJOIN qwen3.5:9b\nreply-b", texts.get(3));
        assertEquals("user", roles.get(4));
        assertEquals("next", texts.get(4));
        assertEquals("assistant", roles.get(5));
        assertEquals("after", texts.get(5));
        assertTrue(texts.get(3).contains("JOIN gemma3:12b"));
        assertTrue(texts.get(3).contains("JOIN qwen3.5:9b"));
    }

    /**
     * The method arranges no objects.
     * The method reads {@link AetherisNotebookPanel#HIDE_COLLAPSED} and {@link AetherisNotebookPanel#SHOW_COLLAPSED}.
     * The hide label is {@code Hide Collapsed}.
     * The show label is {@code Show Collapsed}.
     *
     * @return The method returns nothing.
     */
    @Test
    void labelsSitAfterMood() {
        assertEquals("Hide Collapsed", AetherisNotebookPanel.HIDE_COLLAPSED);
        assertEquals("Show Collapsed", AetherisNotebookPanel.SHOW_COLLAPSED);
    }

    /**
     * The method arranges no cards.
     * The method calls {@link AetherisNotebookPanel#canvasShowsCell}.
     * An open card stays shown when hide is on.
     * A folded card stays shown when hide is off.
     * A folded card is hidden when hide is on.
     *
     * @return The method returns nothing.
     */
    @Test
    void anOpenCardStaysWhileHideIsOn() {
        assertTrue(AetherisNotebookPanel.canvasShowsCell(false, true));
        assertTrue(AetherisNotebookPanel.canvasShowsCell(true, false));
        assertFalse(AetherisNotebookPanel.canvasShowsCell(true, true));
    }

    /**
     * The method arranges a folded card, an open card, a folded group member, and an open merge card.
     * The method calls {@link AetherisNotebookPanel#cellsOnCanvas} with hide true and then with hide false.
     * Hide true returns the open card and then the merge card.
     * Hide false returns all four cards.
     * The first id in that full list is {@code folded}.
     * The third id in that full list is {@code member}.
     *
     * @return The method returns nothing.
     */
    @Test
    void foldedCardsLeaveAndShowBringsThemBack() {
        ArrayList<CellRecord> ordered = new ArrayList<CellRecord>();
        ordered.add(cell("folded", true));
        ordered.add(cell("open", false));
        CellRecord member = cell("member", true);
        member.groupId = "g";
        member.columnPosition = 1;
        CellRecord merge = cell("merge", false);
        merge.groupId = "g";
        ordered.add(member);
        ordered.add(merge);
        ArrayList<CellRecord> hidden = AetherisNotebookPanel.cellsOnCanvas(ordered, true);
        assertEquals(2, hidden.size());
        assertEquals("open", hidden.get(0).id);
        assertEquals("merge", hidden.get(1).id);
        ArrayList<CellRecord> shown = AetherisNotebookPanel.cellsOnCanvas(ordered, false);
        assertEquals(4, shown.size());
        assertEquals("folded", shown.get(0).id);
        assertEquals("member", shown.get(2).id);
    }

    /**
     * The method arranges hide yaml, a temporary directory, and a file named {@code aetheris-hide.yaml}.
     * {@link AetherisNotebookPanel#HIDE_YAML_FILE} is {@code aetheris-hide.yaml}.
     * The method calls {@link AetherisNotebookPanel#parseHideYaml}, {@link AetherisNotebookPanel#renderHideYaml}, {@link AetherisNotebookPanel#writeHideYaml}, and {@link AetherisNotebookPanel#readHideYaml}.
     * Version {@code 1} with {@code hide_collapsed: true} and {@code note: kept} parses with {@code blocked} false and {@code hide} true.
     * The extras contain {@code note: kept}.
     * Rendering that record and parsing it again keeps hide true and keeps the note.
     * {@code hide_collapsed: false} parses with {@code blocked} false and {@code hide} false.
     * Version {@code 2} parses with {@code blocked} true.
     * Writing that blocked record returns false and leaves the file blocked on the next read.
     * A missing file reads with {@code blocked} false and {@code hide} false.
     * Writing the kept record returns true.
     * The following read has hide true and extras that contain {@code note: kept}.
     * Writing that record with hide turned off returns true.
     * The following read has hide false.
     *
     * @return The method returns nothing.
     * @throws Exception The method declares {@link Exception} because the file calls can throw a checked failure.
     */
    @Test
    void theHideChoiceStays() throws Exception {
        assertEquals("aetheris-hide.yaml", AetherisNotebookPanel.HIDE_YAML_FILE);
        HideSeat seat = AetherisNotebookPanel.parseHideYaml("version: 1\nhide_collapsed: true\nnote: kept\n");
        assertFalse(seat.blocked);
        assertTrue(seat.hide);
        assertTrue(seat.extras.contains("note: kept"));
        HideSeat again = AetherisNotebookPanel.parseHideYaml(AetherisNotebookPanel.renderHideYaml(seat));
        assertTrue(again.hide);
        assertTrue(again.extras.contains("note: kept"));
        HideSeat off = AetherisNotebookPanel.parseHideYaml("version: 1\nhide_collapsed: false\n");
        assertFalse(off.blocked);
        assertFalse(off.hide);
        HideSeat blocked = AetherisNotebookPanel.parseHideYaml("version: 2\nhide_collapsed: true\n");
        assertTrue(blocked.blocked);
        File dir = new File(System.getProperty("java.io.tmpdir"), "aetheris-hide-plate");
        if (dir.exists()) {
            File[] kids = dir.listFiles();
            if (kids != null) {
                for (int i = 0; i < kids.length; i++) {
                    kids[i].delete();
                }
            }
        }
        dir.mkdirs();
        File path = new File(dir, "aetheris-hide.yaml");
        byte[] original = "version: 2\nhide_collapsed: true\n".getBytes(StandardCharsets.UTF_8);
        FileOutputStream out = new FileOutputStream(path);
        try {
            out.write(original);
        } finally {
            out.close();
        }
        assertFalse(AetherisNotebookPanel.writeHideYaml(path, blocked));
        HideSeat kept = AetherisNotebookPanel.readHideYaml(path);
        assertTrue(kept.blocked);
        HideSeat missing = AetherisNotebookPanel.readHideYaml(new File(dir, "missing.yaml"));
        assertFalse(missing.blocked);
        assertFalse(missing.hide);
        assertTrue(AetherisNotebookPanel.writeHideYaml(path, seat));
        HideSeat saved = AetherisNotebookPanel.readHideYaml(path);
        assertTrue(saved.hide);
        assertTrue(saved.extras.contains("note: kept"));
        assertTrue(AetherisNotebookPanel.writeHideYaml(path, saved.withHide(false)));
        assertFalse(AetherisNotebookPanel.readHideYaml(path).hide);
    }
}
