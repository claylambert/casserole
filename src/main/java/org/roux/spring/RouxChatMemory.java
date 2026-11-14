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

// @aa-sdd PK-1269 docs/specs/java/org/roux/spring/spring.spec.md
/**
 * The RouxChatMemory stores Spring chat messages by calling {@link RouxEngine}.
 * The full history is stored.
 * A window is applied when a caller reads.
 * {@link #add} maps each message, counts its tokens, and appends it.
 * {@link #closeConversation} asks the engine to close the region.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#RouxChatMemory roux-design.md
 *
 * @author clayl (2026-08-25)
 */
@Component
// @aa-sdd PK-636 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-944 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-687 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-804 docs/specs/common/roux.spec.md
// @aa-sdd PK-813 docs/specs/common/roux.spec.md
// @aa-sdd PK-819 docs/specs/common/roux.spec.md
// @aa-sdd PK-533 docs/specs/java/org/roux/spring/spring.spec.md
// @aa-sdd PK-810 docs/specs/common/roux.spec.md
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
     * // @aa-sdd G-08 aetheris-stages-1-3.finalize.md
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
