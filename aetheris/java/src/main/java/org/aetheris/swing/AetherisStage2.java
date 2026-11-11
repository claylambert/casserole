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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.aetheris.vault.CellRecord;
import org.aetheris.vault.CellState;

// @aa-sdd PK-3888 docs/specs/java/org/aetheris/swing/swing.spec.md
/**
 * The {@code AetherisStage2} class holds the notebook rules the canvas shares.
 * A pin search splits text into tokens and matches every token.
 * A pipe replaces {@code {{cell[k].output}}} from an earlier cell.
 * CONCAT joins member replies under a {@code JOIN} line.
 * The version-1 exchange writes and reads a canvas as line text.
 * // @aa-sdd PK-3818 PK-4416 PK-4423 PK-3808 PK-4412 PK-4418 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-4438 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 *
 * @author clayl (2026-10-20)
 */
public final class AetherisStage2 {

    /**
     * The constant is the old host sentence.
     * A new working set does not send this sentence.
     * A blank prompt drops it.
     */
    public static final String HOST_SYSTEM = "You are the Casserole assistant. Be concise and useful.";

    /**
     * The constructor stops a caller from creating an instance.
     * The rules are static.
     */
    private AetherisStage2() {
    }

    /**
     * The method splits a pin field into tokens.
     * A letter or digit stays in the current token.
     * Any other character ends the token.
     * Each token is stored in lower case.
     * The words {@code a}, {@code the}, {@code and}, {@code of}, and {@code to} are dropped.
     * A null field returns an empty list.
     *
     * @param raw The raw value is the pin field, and it may be null.
     * @return The return value is the remaining tokens.
     */
    public static List<String> pinTokens(String raw) {
        ArrayList<String> out = new ArrayList<String>();
        if (raw == null) {
            return out;
        }
        StringBuilder cur = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char ch = raw.charAt(i);
            if (Character.isLetterOrDigit(ch)) {
                cur.append(ch);
            } else {
                pushToken(out, cur);
            }
        }
        pushToken(out, cur);
        return out;
    }

    /**
     * The method reports whether every pin token occurs in the cell text.
     * A null cell returns false.
     * An unreadable cell returns false.
     * An ephemeral cell is not searched.
     * A null or empty token list returns false.
     * The search text is the user text, a newline, and the assistant text, in lower case.
     * A missing token returns false.
     * Every token present returns true.
     *
     * @param cell The cell is the row to search, and it may be null.
     * @param tokens The tokens are the pin tokens from {@link #pinTokens}.
     * @return The return value is true when every token occurs in the input or the output.
     */
    public static boolean pinMatch(CellRecord cell, List<String> tokens) {
        if (cell == null || !cell.readable || cell.ephemeral) {
            return false;
        }
        if (tokens == null || tokens.size() == 0) {
            return false;
        }
        String hay = ((cell.userText == null ? "" : cell.userText) + "\n"
                + (cell.assistantText == null ? "" : cell.assistantText)).toLowerCase();
        for (int i = 0; i < tokens.size(); i++) {
            if (hay.indexOf(tokens.get(i)) < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method reports whether a cell stays in the source list.
     * A null cell returns false.
     * An unreadable cell returns false.
     * {@link CellState#EXCLUDED} returns false.
     * An ephemeral cell whose user text and assistant text are both blank returns false.
     * Any other cell returns true.
     *
     * @param cell The cell is the row being considered, and it may be null.
     * @return The return value is true when the cell stays in the source list.
     */
    public static boolean kept(CellRecord cell) {
        if (cell == null || !cell.readable) {
            return false;
        }
        if (cell.state == CellState.EXCLUDED) {
            return false;
        }
        if (cell.ephemeral && blank(cell.userText) && blank(cell.assistantText)) {
            return false;
        }
        return true;
    }

    /**
     * The method returns the provider id from a stored execution target.
     * A null target returns {@code ollama}.
     * A blank target returns {@code ollama}.
     * The id is the text before the first {@code /}.
     * A target with no slash uses the whole trimmed target.
     * The id is returned in lower case.
     *
     * @param target The target is the stored execution target, and it may be null.
     * @return The return value is the provider id, and an empty target selects {@code ollama}.
     */
    public static String providerId(String target) {
        if (target == null) {
            return "ollama";
        }
        String trimmed = target.trim();
        if (trimmed.length() == 0) {
            return "ollama";
        }
        int slash = trimmed.indexOf('/');
        String id = slash < 0 ? trimmed : trimmed.substring(0, slash);
        return id.toLowerCase();
    }

    /**
     * The method returns the model id after the first {@code /}.
     * A null target returns empty.
     * A target with no slash returns empty.
     * A slash at the end returns empty.
     * The model id is trimmed.
     *
     * @param target The target is the stored execution target, and it may be null.
     * @return The return value is the model id, or empty.
     */
    public static String modelAfterSlash(String target) {
        if (target == null) {
            return "";
        }
        int slash = target.indexOf('/');
        if (slash < 0 || slash + 1 >= target.length()) {
            return "";
        }
        return target.substring(slash + 1).trim();
    }

    /**
     * The method reports whether the provider is not local Ollama.
     * The provider comes from {@link #providerId}.
     *
     * @param target The target is the stored execution target.
     * @return The return value is true when the provider id is not {@code ollama}.
     */
    public static boolean cloudTarget(String target) {
        return !"ollama".equals(providerId(target));
    }

    /**
     * The method returns the execution target shown for a cell.
     * A null cell returns {@code ollama}.
     * A null or blank target returns {@code ollama}.
     * Any other target is returned trimmed.
     *
     * @param cell The cell is the member, and it may be null.
     * @return The return value is the label text.
     */
    public static String shownTarget(CellRecord cell) {
        if (cell == null || cell.executionTarget == null || cell.executionTarget.trim().length() == 0) {
            return "ollama";
        }
        return cell.executionTarget.trim();
    }

    /**
     * The method returns the short model word used on a join line.
     * An Ollama or Grok target uses the model id after the slash.
     * An empty model id uses the provider id instead.
     * A pool target uses the word {@code pool}.
     * Any other provider uses {@link #shownTarget}.
     * A null cell uses {@link #shownTarget}.
     *
     * @param cell The cell is the member, and it may be null.
     * @return The return value is the model word on the join line.
     */
    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    private static String joinWord(CellRecord cell) {
        String target = cell == null ? null : cell.executionTarget;
        String provider = providerId(target);
        String word = "";
        if ("ollama".equals(provider) || "grok".equals(provider)) {
            String id = modelAfterSlash(target);
            if (id.length() == 0) {
                word = provider;
            } else {
                word = id;
            }
        } else if ("pool".equals(provider)) {
            word = "pool";
        }
        if (word.length() == 0) {
            return shownTarget(cell);
        }
        return word;
    }

    /**
     * The method reports whether one stored line is a join banner.
     * The line must start with {@code JOIN }.
     * The rest of the line must be non-empty.
     * A space in the rest of the line returns false.
     * A null line returns false.
     *
     * @param line The line is one stored line, and it may be null.
     * @return The return value is true when the line is a join banner.
     */
    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
    // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    private static boolean joinBannerLine(String line) {
        if (line == null || !line.startsWith("JOIN ")) {
            return false;
        }
        String rest = line.substring(5);
        if (rest.length() == 0) {
            return false;
        }
        for (int i = 0; i < rest.length(); i++) {
            if (Character.isWhitespace(rest.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method joins member replies into one assistant text.
     * Each part starts with {@code JOIN} and the short model word from {@link #joinWord}.
     * The assistant text of that member follows on the next line.
     * A null assistant text is written as empty.
     * Parts are separated by a blank line.
     *
     * @param members The members are the cells in send order.
     * @return The return value is the merged assistant text.
     */
    public static String concatJoin(List<CellRecord> members) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < members.size(); i++) {
            CellRecord cell = members.get(i);
            if (i > 0) {
                sb.append("\n\n");
            }
            sb.append("JOIN ").append(joinWord(cell)).append("\n");
            sb.append(cell.assistantText == null ? "" : cell.assistantText);
        }
        return sb.toString();
    }

    /**
     * The method drops the {@code JOIN} lines that {@link #concatJoin} writes.
     * A null or empty merge returns empty.
     * A line that {@link #joinBannerLine} accepts is dropped.
     * A line equal to {@code JOIN} plus {@link #shownTarget} for a member is also dropped.
     * Every other line stays, including the reply text.
     * A null member list drops only the short-banner lines.
     *
     * @param merged The merged text is the stored merge reply, and it may be null.
     * @param members The members are the group cells in canvas order, and the list may be null.
     * @return The return value is the reply text without those banners.
     */
    public static String stripJoinBanners(String merged, List<CellRecord> members) {
        if (merged == null || merged.length() == 0) {
            return "";
        }
        ArrayList<String> banners = new ArrayList<String>();
        if (members != null) {
            for (int i = 0; i < members.size(); i++) {
                banners.add("JOIN " + shownTarget(members.get(i)));
            }
        }
        StringBuilder sb = new StringBuilder();
        int start = 0;
        for (int i = 0; i <= merged.length(); i++) {
            if (i < merged.length() && merged.charAt(i) != '\n') {
                continue;
            }
            String line = merged.substring(start, i);
            boolean banner = joinBannerLine(line);
            for (int b = 0; b < banners.size() && !banner; b++) {
                if (line.equals(banners.get(b))) {
                    banner = true;
                }
            }
            if (!banner) {
                if (sb.length() > 0) {
                    sb.append('\n');
                }
                sb.append(line);
            }
            start = i + 1;
        }
        return sb.toString();
    }

    /**
     * The method replaces {@code {{cell[k].output}}} from kept cells.
     * {@code k} is 1-based.
     * A failure string means the pipe does not send.
     * The kept list is the source cells in send order, without the target cell.
     *
     * @param text The text is the prompt, and it may be null.
     * @param kept The kept list is those source cells.
     * @return The return value is the resolved text, or a failure that starts with {@code pipe:}.
     */
    public static String resolvePipes(String text, List<CellRecord> kept) {
        ArrayList<String> stack = new ArrayList<String>();
        return resolvePipes(text, kept, stack);
    }

    /**
     * The method collects source cells that sit before the target.
     * The scan stops at the target id.
     * A cell that {@link #kept} rejects is skipped.
     * A cloud send also skips an ephemeral cell.
     * A cell in the same non-empty group as the target is skipped.
     *
     * @param ordered The ordered list is the cells in send order.
     * @param target The target is the cell being sent, and it may be null.
     * @param cloud The flag is true when ephemeral text is omitted.
     * @return The return value is the kept sources before the target.
     */
    public static List<CellRecord> sourcesBefore(List<CellRecord> ordered, CellRecord target, boolean cloud) {
        ArrayList<CellRecord> kept = new ArrayList<CellRecord>();
        for (int i = 0; i < ordered.size(); i++) {
            CellRecord cell = ordered.get(i);
            if (target != null && cell.id.equals(target.id)) {
                break;
            }
            if (!kept(cell)) {
                continue;
            }
            if (cloud && cell.ephemeral) {
                continue;
            }
            if (sameGroup(cell, target)) {
                continue;
            }
            kept.add(cell);
        }
        return kept;
    }

    /**
     * The method writes a version-1 canvas body.
     * The first line is {@code version: 1}.
     * The canvas name follows.
     * Each cell writes its fields, including an empty group id.
     * An ephemeral cell omits the user and assistant text unless {@code keepBurners} is true.
     * A null state is written as {@code INCLUDED}.
     *
     * @param name The name is the canvas name.
     * @param cells The cells are the rows in send order.
     * @param keepBurners The flag is true when burner bodies stay in the file.
     * @return The return value is the line text.
     */
    public static String exportToon(String name, List<CellRecord> cells, boolean keepBurners) {
        StringBuilder sb = new StringBuilder();
        sb.append("version: 1\n");
        sb.append("canvas: ").append(esc(name)).append("\n");
        sb.append("cells:\n");
        for (int i = 0; i < cells.size(); i++) {
            CellRecord cell = cells.get(i);
            boolean strip = cell.ephemeral && !keepBurners;
            sb.append("- id: ").append(esc(cell.id)).append("\n");
            sb.append("  turn_index: ").append(cell.turnIndex).append("\n");
            sb.append("  parent_cell_id: ").append(esc(cell.parentCellId)).append("\n");
            sb.append("  branch_id: ").append(esc(cell.branchId)).append("\n");
            sb.append("  column_position: ").append(cell.columnPosition).append("\n");
            sb.append("  group_id: ").append(esc(cell.groupId)).append("\n");
            sb.append("  execution_target: ").append(esc(cell.executionTarget)).append("\n");
            sb.append("  context_state: ").append(cell.state == null ? "INCLUDED" : cell.state.name()).append("\n");
            sb.append("  ephemeral: ").append(cell.ephemeral ? "true" : "false").append("\n");
            sb.append("  collapsed: ").append(cell.collapsed ? "true" : "false").append("\n");
            sb.append("  execution_status: ").append(esc(cell.executionStatus)).append("\n");
            sb.append("  user_clob: ").append(esc(strip ? "" : cell.userText)).append("\n");
            sb.append("  assistant_clob: ").append(esc(strip ? "" : cell.assistantText)).append("\n");
        }
        return sb.toString();
    }

    /**
     * The method imports a canvas as a copy.
     * A body whose version is not 1 returns an empty list.
     * Each cell id becomes a new UUID.
     * Each non-empty group id becomes one new UUID shared by that group.
     * A parent id is rewritten to the new id, or cleared when the parent is missing.
     * A blank branch id becomes {@code main}.
     * Pipe indexes in the text are rewritten to the new kept-cell order.
     * A pipe whose old index has no new cell is left unchanged.
     *
     * @param raw The raw text is the file body.
     * @param canvasId The canvas id is the destination canvas.
     * @return The return value is the copied cells, or empty when the version is not 1.
     */
    public static List<CellRecord> importToon(String raw, String canvasId) {
        ArrayList<CellRecord> parsed = parseExport(raw);
        if (parsed.size() == 0) {
            return parsed;
        }
        ArrayList<String> oldKeptIds = new ArrayList<String>();
        for (int i = 0; i < parsed.size(); i++) {
            if (kept(parsed.get(i))) {
                oldKeptIds.add(parsed.get(i).id);
            }
        }
        HashMap<String, String> ids = new HashMap<String, String>();
        HashMap<String, String> groups = new HashMap<String, String>();
        for (int i = 0; i < parsed.size(); i++) {
            CellRecord cell = parsed.get(i);
            String previous = cell.id;
            String fresh = UUID.randomUUID().toString();
            ids.put(previous, fresh);
            cell.id = fresh;
            cell.canvasId = canvasId;
            if (cell.groupId != null && cell.groupId.length() > 0) {
                String mapped = groups.get(cell.groupId);
                if (mapped == null) {
                    mapped = UUID.randomUUID().toString();
                    groups.put(cell.groupId, mapped);
                }
                cell.groupId = mapped;
            }
        }
        for (int i = 0; i < parsed.size(); i++) {
            CellRecord cell = parsed.get(i);
            if (cell.parentCellId != null && cell.parentCellId.length() > 0) {
                String mapped = ids.get(cell.parentCellId);
                cell.parentCellId = mapped == null ? "" : mapped;
            }
            if (cell.branchId == null || cell.branchId.length() == 0) {
                cell.branchId = "main";
            }
        }
        ArrayList<CellRecord> neuKept = new ArrayList<CellRecord>();
        for (int i = 0; i < parsed.size(); i++) {
            if (kept(parsed.get(i))) {
                neuKept.add(parsed.get(i));
            }
        }
        HashMap<Integer, Integer> kMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < oldKeptIds.size(); i++) {
            String neuId = ids.get(oldKeptIds.get(i));
            if (neuId == null) {
                continue;
            }
            for (int n = 0; n < neuKept.size(); n++) {
                if (neuId.equals(neuKept.get(n).id)) {
                    kMap.put(Integer.valueOf(i + 1), Integer.valueOf(n + 1));
                }
            }
        }
        for (int i = 0; i < parsed.size(); i++) {
            CellRecord cell = parsed.get(i);
            cell.userText = rewriteK(cell.userText, kMap);
            cell.assistantText = rewriteK(cell.assistantText, kMap);
        }
        return parsed;
    }

    /**
     * The method replaces every {@code {{cell[k].output}}} in one text.
     * A null text returns empty.
     * Text with no pipe marker is copied through.
     * A marker without a closing {@code ].output}} is copied through.
     * A {@code k} that is not an integer returns {@code pipe: bad k}.
     * A {@code k} below 1 or past the kept list returns {@code pipe: bad k}.
     * An ephemeral source returns {@code pipe: ephemeral}.
     * A source whose status is {@code FAILED} returns {@code pipe: failed}.
     * An empty assistant text returns {@code pipe: empty}.
     * A source already on the stack returns {@code pipe: cycle}.
     * A nested failure is returned as that same failure string.
     *
     * @param text The text is the prompt or a nested assistant text, and it may be null.
     * @param kept The kept list is the source cells.
     * @param stack The stack holds source ids already being expanded.
     * @return The return value is the resolved text, or a failure that starts with {@code pipe:}.
     */
    private static String resolvePipes(String text, List<CellRecord> kept, ArrayList<String> stack) {
        if (text == null) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            int start = text.indexOf("{{cell[", i);
            if (start < 0) {
                out.append(text.substring(i));
                break;
            }
            int end = text.indexOf("].output}}", start);
            if (end < 0) {
                out.append(text.substring(i));
                break;
            }
            out.append(text.substring(i, start));
            String num = text.substring(start + "{{cell[".length(), end).trim();
            int k;
            try {
                k = Integer.parseInt(num);
            } catch (NumberFormatException ex) {
                return "pipe: bad k";
            }
            if (k < 1 || kept == null || k > kept.size()) {
                return "pipe: bad k";
            }
            CellRecord src = kept.get(k - 1);
            if (src.ephemeral) {
                return "pipe: ephemeral";
            }
            if ("FAILED".equals(src.executionStatus)) {
                return "pipe: failed";
            }
            String clob = src.assistantText == null ? "" : src.assistantText;
            if (clob.length() == 0) {
                return "pipe: empty";
            }
            if (stack.contains(src.id)) {
                return "pipe: cycle";
            }
            stack.add(src.id);
            String inner = resolvePipes(clob, kept, stack);
            stack.remove(stack.size() - 1);
            if (inner.startsWith("pipe:")) {
                return inner;
            }
            out.append(inner);
            i = end + "].output}}".length();
        }
        return out.toString();
    }

    /**
     * The method rewrites pipe indexes after an import copy.
     * A null text returns empty.
     * An empty text returns empty.
     * A marker that is not an integer is copied unchanged.
     * A {@code k} missing from the map is copied unchanged.
     * A mapped {@code k} is written with the new index.
     *
     * @param text The text is the user or assistant text, and it may be null.
     * @param kMap The map sends an old 1-based index to the new 1-based index.
     * @return The return value is the rewritten text.
     */
    private static String rewriteK(String text, HashMap<Integer, Integer> kMap) {
        if (text == null || text.length() == 0) {
            return text == null ? "" : text;
        }
        StringBuilder out = new StringBuilder();
        int i = 0;
        while (i < text.length()) {
            int start = text.indexOf("{{cell[", i);
            if (start < 0) {
                out.append(text.substring(i));
                break;
            }
            int end = text.indexOf("].output}}", start);
            if (end < 0) {
                out.append(text.substring(i));
                break;
            }
            out.append(text.substring(i, start));
            String num = text.substring(start + "{{cell[".length(), end).trim();
            try {
                int k = Integer.parseInt(num);
                Integer mapped = kMap.get(Integer.valueOf(k));
                if (mapped != null) {
                    out.append("{{cell[").append(mapped.intValue()).append("].output}}");
                } else {
                    out.append(text.substring(start, end + "].output}}".length()));
                }
            } catch (NumberFormatException ex) {
                out.append(text.substring(start, end + "].output}}".length()));
            }
            i = end + "].output}}".length();
        }
        return out.toString();
    }

    /**
     * The method parses a version-1 export into cells.
     * A null or empty body returns an empty list.
     * A first line that does not start with {@code version:} returns an empty list.
     * A version other than {@code 1} returns an empty list.
     * A line that starts with {@code - id:} begins a cell.
     * That cell starts readable, included, and on branch {@code main}.
     * A field line before the first id is ignored.
     * {@code context_state} sets {@code readable} from {@link CellState#knownToken} and the state from {@link CellState#fromToken}.
     * A bad integer keeps the current turn index or stores column 0.
     *
     * @param raw The raw text is the file body, and it may be null.
     * @return The return value is the parsed cells.
     */
    private static ArrayList<CellRecord> parseExport(String raw) {
        ArrayList<CellRecord> out = new ArrayList<CellRecord>();
        if (raw == null || raw.length() == 0) {
            return out;
        }
        String[] lines = raw.split("\n", -1);
        if (lines.length == 0 || !lines[0].startsWith("version:")) {
            return out;
        }
        String ver = lines[0].substring("version:".length()).trim();
        if (!"1".equals(ver)) {
            return out;
        }
        CellRecord current = null;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String trimmed = line.trim();
            if (trimmed.startsWith("- id:")) {
                current = new CellRecord();
                current.id = unesc(trimmed.substring("- id:".length()).trim());
                current.readable = true;
                current.state = CellState.INCLUDED;
                current.branchId = "main";
                out.add(current);
                continue;
            }
            if (current == null) {
                continue;
            }
            if (trimmed.startsWith("turn_index:")) {
                current.turnIndex = parseInt(trimmed.substring("turn_index:".length()).trim(), current.turnIndex);
            } else if (trimmed.startsWith("parent_cell_id:")) {
                current.parentCellId = unesc(trimmed.substring("parent_cell_id:".length()).trim());
            } else if (trimmed.startsWith("branch_id:")) {
                current.branchId = unesc(trimmed.substring("branch_id:".length()).trim());
            } else if (trimmed.startsWith("column_position:")) {
                current.columnPosition = parseInt(trimmed.substring("column_position:".length()).trim(), 0);
            } else if (trimmed.startsWith("group_id:")) {
                current.groupId = unesc(trimmed.substring("group_id:".length()).trim());
            } else if (trimmed.startsWith("execution_target:")) {
                current.executionTarget = unesc(trimmed.substring("execution_target:".length()).trim());
            } else if (trimmed.startsWith("context_state:")) {
                String token = unesc(trimmed.substring("context_state:".length()).trim());
                current.readable = CellState.knownToken(token);
                current.state = CellState.fromToken(token);
            } else if (trimmed.startsWith("ephemeral:")) {
                current.ephemeral = truth(trimmed.substring("ephemeral:".length()).trim());
            } else if (trimmed.startsWith("collapsed:")) {
                current.collapsed = truth(trimmed.substring("collapsed:".length()).trim());
            } else if (trimmed.startsWith("execution_status:")) {
                current.executionStatus = unesc(trimmed.substring("execution_status:".length()).trim());
            } else if (trimmed.startsWith("user_clob:")) {
                current.userText = unesc(trimmed.substring("user_clob:".length()).trim());
            } else if (trimmed.startsWith("assistant_clob:")) {
                current.assistantText = unesc(trimmed.substring("assistant_clob:".length()).trim());
            }
        }
        return out;
    }

    /**
     * The method reports whether two cells share a non-empty group id.
     * A null cell returns false.
     * A null target returns false.
     * An empty group id returns false.
     *
     * @param cell The cell is the candidate source.
     * @param target The target is the cell being sent.
     * @return The return value is true when both group ids are the same non-empty id.
     */
    private static boolean sameGroup(CellRecord cell, CellRecord target) {
        if (cell == null || target == null) {
            return false;
        }
        if (cell.groupId == null || cell.groupId.length() == 0) {
            return false;
        }
        return cell.groupId.equals(target.groupId);
    }

    /**
     * The method stores one finished token and clears the buffer.
     * An empty buffer does nothing.
     * The token is stored in lower case.
     * {@code a}, {@code the}, {@code and}, {@code of}, and {@code to} are not stored.
     *
     * @param out The list receives the token.
     * @param cur The buffer holds the characters gathered so far.
     */
    private static void pushToken(List<String> out, StringBuilder cur) {
        if (cur.length() == 0) {
            return;
        }
        String token = cur.toString().toLowerCase();
        cur.setLength(0);
        if (token.equals("a") || token.equals("the") || token.equals("and") || token.equals("of")
                || token.equals("to")) {
            return;
        }
        out.add(token);
    }

    /**
     * The method reports whether a text is missing or only whitespace.
     *
     * @param raw The raw value is the text, and it may be null.
     * @return The return value is true when the text is null or blank after trim.
     */
    private static boolean blank(String raw) {
        return raw == null || raw.trim().length() == 0;
    }

    /**
     * The method reads a boolean token from an export line.
     * {@code true} and {@code yes} match in any letter case.
     * The token {@code 1} matches only that exact character.
     *
     * @param raw The raw value is the text after the colon.
     * @return The return value is true for {@code true}, {@code yes}, or {@code 1}.
     */
    private static boolean truth(String raw) {
        return "true".equalsIgnoreCase(raw) || "yes".equalsIgnoreCase(raw) || "1".equals(raw);
    }

    /**
     * The method parses an integer from an export field.
     * A value that is not an integer returns the fallback.
     *
     * @param raw The raw value is the field text.
     * @param fallback The fallback is the value used when parsing fails.
     * @return The return value is the integer, or the fallback.
     */
    private static int parseInt(String raw, int fallback) {
        try {
            return Integer.parseInt(raw);
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    /**
     * The method escapes one export field.
     * A null value returns empty.
     * A backslash becomes two backslashes.
     * A newline becomes {@code \\n}.
     * A carriage return is removed.
     *
     * @param raw The raw value is the field text, and it may be null.
     * @return The return value is the escaped text.
     */
    private static String esc(String raw) {
        if (raw == null) {
            return "";
        }
        return raw.replace("\\", "\\\\").replace("\n", "\\n").replace("\r", "");
    }

    /**
     * The method reverses {@link #esc}.
     * A null value returns empty.
     * {@code \\n} becomes a newline.
     * A doubled backslash becomes one backslash.
     *
     * @param raw The raw value is the escaped field, and it may be null.
     * @return The return value is the decoded text.
     */
    private static String unesc(String raw) {
        if (raw == null) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            if (c == '\\' && i + 1 < raw.length()) {
                char n = raw.charAt(i + 1);
                if (n == 'n') {
                    out.append('\n');
                    i++;
                    continue;
                }
                if (n == '\\') {
                    out.append('\\');
                    i++;
                    continue;
                }
            }
            out.append(c);
        }
        return out.toString();
    }
}
/*****************************************************************************************
 * End of Class: AetherisStage2.java 102026
 ****************************************************************************************/
