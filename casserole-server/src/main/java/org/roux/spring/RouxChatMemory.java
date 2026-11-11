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

package org.roux.spring;

import java.util.ArrayList;
import java.util.List;

import org.roux.RouxEngine;
import org.roux.model.DecodedFrame;
import org.roux.model.Role;
import org.roux.session.OpenResult;
import org.roux.token.TokenCounter;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.stereotype.Component;

// @aa-sdd PK-0861 PK-0908 PK-0955 PK-1002 PK-1049 PK-1096 PK-1143 PK-1190 PK-1237 PK-1284 PK-1331 PK-1378 PK-1425 PK-1472 PK-1519 PK-1566 PK-1614 PK-1661 PK-1708 PK-1755 PK-1802 PK-1849 PK-1896 PK-1943 PK-1990 PK-2037 PK-2085 PK-2132 PK-2179 PK-2226 PK-2274 PK-2322 PK-2370 PK-2417 PK-2464 PK-2512 PK-2559 PK-2606 PK-2653 PK-2701 PK-2748 PK-2796 PK-2843 PK-2890 PK-2937 PK-2984 PK-3031 PK-3078 PK-3125 PK-3172 PK-3220 PK-3267 PK-3314 PK-3362 PK-3409 PK-3456 docs/specs/java/org/roux/roux.spec.md
/**
 * The RouxChatMemory stores Spring chat messages by calling {@link RouxEngine}.
 * The full history is stored.
 * A window is applied when a caller reads.
 * {@link #add} maps each message, counts its tokens, and appends it.
 * {@link #closeConversation} asks the engine to close the region.
 *
 * // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
 * // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
 * // @aa-sdd PK-0462 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
 * // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
 * // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
 * // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
 * // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
 * // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
 * // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
 * // @aa-sdd PK-4436 PK-4437 docs/specs/rust/aetheris/aetheris-term/aetheris-term.spec.md
 * // @aa-sdd PK-4441 docs/specs/rust/aetheris/aetheris-client/aetheris-client.spec.md
 * // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
 *
 * @author clayl (2026-08-25)
 */
@Component
public class RouxChatMemory implements ChatMemory {

    private final RouxEngine engine;
    private final TokenCounter tokenCounter;

    /**
     * The constructor stores the engine and the token counter.
     *
     * @param engine The engine is the {@link RouxEngine} that stores the messages.
     * @param tokenCounter The counter is the {@link TokenCounter} used on each add.
     */
    public RouxChatMemory(RouxEngine engine, TokenCounter tokenCounter) {
        this.engine = engine;
        this.tokenCounter = tokenCounter;
    }

    /**
     * The method appends each Spring message.
     * A null conversation id returns without writing.
     * A null list returns without writing.
     * A null message inside the list is skipped.
     * Every message in one call shares one timestamp.
     *
     * @param conversationId The conversation id is the region name.
     * @param messages The messages are the Spring chat messages to store.
     */
    @Override
    public void add(String conversationId, List<Message> messages) {
        if (conversationId == null || messages == null) {
            return;
        }
        long now = System.currentTimeMillis();
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message == null) {
                continue;
            }
            String text = RouxMessageMapper.textOf(message);
            byte role = RouxMessageMapper.roleOf(message);
            int tokens = tokenCounter.count(text);
            engine.appendMessage(conversationId, role, now, tokens, text);
        }
    }

    /**
     * The method returns the stored messages with no message cap and no system pin.
     *
     * @param conversationId The conversation id is the region name.
     * @return The list is the messages in stored order.
     */
    @Override
    public List<Message> get(String conversationId) {
        return getWindow(conversationId, 0, false);
    }

    /**
     * The method clears the frames and keeps the region.
     * A null conversation id returns without a change.
     *
     * @param conversationId The conversation id is the region name.
     */
    @Override
    public void clear(String conversationId) {
        if (conversationId == null) {
            return;
        }
        engine.clearRegion(conversationId);
    }

    /**
     * The method opens or restores a region.
     *
     * @param regionId The region id is the name passed to the engine.
     * @return The result is new, restored, or refused.
     */
    public OpenResult open(String regionId) {
        return engine.openRegion(regionId);
    }

    /**
     * The method opens a region that stays in memory.
     * The call does not take a file lock.
     * The call does not open a disk pack.
     *
     * // @aa-sdd PK-0472 docs/specs/java/org/aetheris/wire/wire.spec.md
     * // @aa-sdd PK-3469 PK-3474 PK-3484 docs/specs/java/org/roux/roux.spec.md
     *
     * @param regionId The region id is the name passed to the engine.
     * @return The result is the open result from the engine.
     */
    public OpenResult openRam(String regionId) {
        return engine.openRam(regionId);
    }

    /**
     * The method reports whether this process keeps regions in memory only.
     *
     * @return The flag is true when the engine is memory-only.
     */
    public boolean isRamOnly() {
        return engine.isRamOnly();
    }

    /**
     * The method closes the region through the engine.
     * A null conversation id returns without a change.
     * A disk region keeps its files when persist is on.
     *
     * @param conversationId The conversation id is the region name.
     */
    public void persistClose(String conversationId) {
        if (conversationId == null) {
            return;
        }
        engine.persistClose(conversationId);
    }

    /**
     * The method closes the region.
     * The call uses {@link #persistClose}.
     *
     * @param conversationId The conversation id is the region name.
     */
    public void closeConversation(String conversationId) {
        persistClose(conversationId);
    }

    /**
     * The method returns a window with no token cap.
     * A {@code maxNonSystem} of {@code 0} or less means every non-system message.
     *
     * @param conversationId The conversation id is the region name.
     * @param maxNonSystem The cap is the maximum number of non-system messages.
     * @param pinSystem The flag is true when system messages stay in front.
     * @return The list is the selected messages.
     */
    public List<Message> getWindow(String conversationId, int maxNonSystem, boolean pinSystem) {
        return getByTokenBudget(conversationId, maxNonSystem, pinSystem, 0);
    }

    /**
     * The method returns a window limited by message count and token count.
     * A token cap of {@code 0} or less means no token limit.
     *
     * @param conversationId The conversation id is the region name.
     * @param maxNonSystem The cap is the maximum number of non-system messages.
     * @param pinSystem The flag is true when system messages stay in front.
     * @param maxTokens The cap is the token budget, or {@code 0} for no token limit.
     * @return The list is the selected messages.
     */
    public List<Message> getWindow(String conversationId, int maxNonSystem, boolean pinSystem, int maxTokens) {
        return getByTokenBudget(conversationId, maxNonSystem, pinSystem, maxTokens);
    }

    /**
     * The method applies a head count on the region.
     * A null conversation id returns without a change.
     *
     * @param conversationId The conversation id is the region name.
     * @param count The count is the head count passed to the engine.
     */
    public void applyHeadCount(String conversationId, int count) {
        if (conversationId == null) {
            return;
        }
        engine.applyHeadCount(conversationId, count);
    }

    /**
     * The method reports whether a locked prefix is stored.
     * A null conversation id returns false.
     *
     * @param conversationId The conversation id is the region name.
     * @return The flag is true when a locked prefix is stored.
     */
    public boolean headDesignated(String conversationId) {
        if (conversationId == null) {
            return false;
        }
        return engine.headDesignated(conversationId);
    }

    /**
     * The method returns the mark for one message.
     * A null conversation id returns an empty string.
     *
     * @param conversationId The conversation id is the region name.
     * @param seq The sequence number is the stored message id.
     * @return The mark is the text from the engine, or an empty string.
     */
    public String headMark(String conversationId, long seq) {
        if (conversationId == null) {
            return "";
        }
        return engine.headMark(conversationId, seq);
    }

    /**
     * The method locks one message.
     * A null conversation id returns {@code No message seq } plus the sequence number.
     *
     * @param conversationId The conversation id is the region name.
     * @param seq The sequence number is the stored message id.
     * @return The result is null when the lock was stored, or one refusal sentence.
     */
    public String lockMessage(String conversationId, long seq) {
        if (conversationId == null) {
            return "No message seq " + seq;
        }
        return engine.lockMessage(conversationId, seq);
    }

    /**
     * The method unlocks one message.
     * A null conversation id returns {@code No message seq } plus the sequence number.
     *
     * @param conversationId The conversation id is the region name.
     * @param seq The sequence number is the stored message id.
     * @return The result is null when the lock was removed, or one refusal sentence.
     */
    public String unlockMessage(String conversationId, long seq) {
        if (conversationId == null) {
            return "No message seq " + seq;
        }
        return engine.unlockMessage(conversationId, seq);
    }

    /**
     * The method replaces the body of one stored message.
     * A null conversation id returns a failure.
     * The failure text is {@code No message seq } plus the sequence number.
     *
     * @param conversationId The conversation id is the region name.
     * @param seq The sequence number is the stored message id.
     * @param text The text is the new body.
     * @return The result is the engine result, or a failure for a null conversation.
     */
    public org.roux.model.OperationResult editMessage(String conversationId, long seq, String text) {
        if (conversationId == null) {
            return org.roux.model.OperationResult.fail(org.roux.model.OperationStatus.NOT_FOUND,
                    "No message seq " + seq);
        }
        return engine.editMessage(conversationId, seq, text);
    }

    /**
     * The method asks the engine to choose which messages fit a token allowance.
     * A null conversation id returns null.
     *
     * @param conversationId The conversation id is the region name.
     * @param managedTokens The allowance is the token budget.
     * @param liveTokens The live count is the token count of the unsent line.
     * @return The cut is the selection, or null when no selection is made.
     */
    public RouxEngine.SinkCut selectTape(String conversationId, int managedTokens, int liveTokens) {
        if (conversationId == null) {
            return null;
        }
        return engine.selectTape(conversationId, managedTokens, liveTokens);
    }

    /**
     * The method reads the stored messages named by a cut.
     * The messages stay in the cut's order.
     * An ordinal that is not a stored message is skipped.
     * A null conversation id returns an empty list.
     * A null cut returns an empty list.
     *
     * @param conversationId The conversation id is the region name.
     * @param cut The cut is the selection from the engine.
     * @return The list is the stored messages in that order.
     */
    public List<Message> sinkMessages(String conversationId, RouxEngine.SinkCut cut) {
        List<Message> out = new ArrayList<Message>();
        if (conversationId == null || cut == null || cut.ordinals == null) {
            return out;
        }
        for (int i = 0; i < cut.ordinals.length; i++) {
            long seq = engine.messageSeqAt(conversationId, cut.ordinals[i]);
            if (seq <= 0L) {
                continue;
            }
            DecodedFrame frame = engine.readMessage(conversationId, seq);
            if (frame != null) {
                out.add(RouxMessageMapper.toSpring(frame));
            }
        }
        return out;
    }

    /**
     * The method reads the window the engine selects.
     * A null conversation id returns an empty list.
     * A frame that cannot be decoded is skipped.
     *
     * @param conversationId The conversation id is the region name.
     * @param maxNonSystem The cap is the maximum number of non-system messages.
     * @param pinSystem The flag is true when system messages stay in front.
     * @param maxTokens The cap is the token budget, or {@code 0} for no token limit.
     * @return The list is the selected messages.
     */
    public List<Message> getByTokenBudget(String conversationId, int maxNonSystem, boolean pinSystem, int maxTokens) {
        List<Message> out = new ArrayList<Message>();
        if (conversationId == null) {
            return out;
        }
        long[] ids = engine.windowIds(conversationId, maxNonSystem, pinSystem, maxTokens);
        for (int i = 0; i < ids.length; i++) {
            DecodedFrame frame = engine.readMessage(conversationId, ids[i]);
            if (frame != null) {
                out.add(RouxMessageMapper.toSpring(frame));
            }
        }
        return out;
    }

    /**
     * The method counts stored messages with one role.
     * A null conversation id returns {@code 0}.
     *
     * @param conversationId The conversation id is the region name.
     * @param role The role is the byte to match.
     * @return The count is the number of matching messages.
     */
    public int countByRole(String conversationId, byte role) {
        if (conversationId == null) {
            return 0;
        }
        return engine.countByRole(conversationId, role);
    }

    /**
     * The method returns the message table for the region.
     * A null conversation id returns an empty string.
     *
     * @param conversationId The conversation id is the region name.
     * @return The table is the text from the engine.
     */
    public String exportToon(String conversationId) {
        if (conversationId == null) {
            return "";
        }
        return engine.exportToon(conversationId);
    }

    /**
     * The method asks the engine to publish the hot page.
     * A null conversation id returns without a change.
     * The engine returns at once when the pack is off, the process is memory-only, or the durability is {@code hot-only}.
     *
     * @param conversationId The conversation id is the region name.
     */
    public void checkpoint(String conversationId) {
        if (conversationId == null) {
            return;
        }
        engine.checkpoint(conversationId);
    }

    /**
     * The method returns the inspect view of one message.
     *
     * @param conversationId The conversation id is the region name.
     * @param seq The sequence number is the stored message id.
     * @return The view is the inspect result, or null.
     */
    public org.roux.model.MessageInspect inspect(String conversationId, long seq) {
        return engine.inspect(conversationId, seq);
    }

    /**
     * The method finds a message by its two identity longs and returns the inspect view.
     * A sequence number below {@code 1} returns null.
     *
     * @param conversationId The conversation id is the region name.
     * @param hi The high long is the first 64 bits of the identity.
     * @param lo The low long is the second 64 bits of the identity.
     * @return The view is the inspect result, or null.
     */
    public org.roux.model.MessageInspect inspectById(String conversationId, long hi, long lo) {
        long seq = engine.findSeq(conversationId, new org.roux.id.RouxId(hi, lo));
        if (seq < 1) {
            return null;
        }
        return engine.inspect(conversationId, seq);
    }

    /**
     * The method returns the status line for one region.
     * A null conversation id returns an empty string.
     *
     * @param conversationId The conversation id is the region name.
     * @return The line is the status text from the engine.
     */
    public String memoryStatusLine(String conversationId) {
        if (conversationId == null) {
            return "";
        }
        return engine.memoryStatusLine(conversationId);
    }

    /**
     * The method counts messages by a type name.
     * The name {@code SYSTEM} counts the system role.
     * The name {@code USER} counts the user role.
     * The name {@code ASSISTANT} counts the assistant role.
     * Any other name returns {@code 0}.
     *
     * @param conversationId The conversation id is the region name.
     * @param typeName The name is {@code SYSTEM}, {@code USER}, or {@code ASSISTANT}.
     * @return The count is the number of matching messages.
     */
    public int countByTypeName(String conversationId, String typeName) {
        if ("SYSTEM".equals(typeName)) {
            return countByRole(conversationId, Role.SYSTEM);
        }
        if ("USER".equals(typeName)) {
            return countByRole(conversationId, Role.USER);
        }
        if ("ASSISTANT".equals(typeName)) {
            return countByRole(conversationId, Role.ASSISTANT);
        }
        return 0;
    }
}
/*****************************************************************************************
 * End of Class: RouxChatMemory.java 082626
 ****************************************************************************************/
