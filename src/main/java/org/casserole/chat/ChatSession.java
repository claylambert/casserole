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

package org.casserole.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.casserole.mood.MoodDesk;
import org.roux.RouxEngine;
import org.roux.model.OperationResult;
import org.roux.model.Role;
import org.roux.token.HeuristicTokenCounter;
import org.roux.session.OpenResult;
import org.roux.spring.RouxChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

// @aa-sdd PK-1094 docs/specs/common/formal-plan.spec.md FINESSE:f-185
// @aa-sdd PK-1092 docs/specs/common/formal-plan.spec.md FINESSE:f-187
// @aa-sdd PK-1243 docs/specs/java/org/casserole/chat/chat.spec.md FINESSE:f-222
// @aa-sdd PK-1243 docs/specs/java/org/casserole/chat/chat.spec.md
/**
 * The {@code ChatSession} type is the chat state for one SSH connection.
 * SSH is the secure-shell protocol the client used to connect.
 * The message history lives in Roux.
 * Roux is the store that holds the conversation messages.
 * This object holds the session id, the active flag, the system prompt, and the prompt assembly.
 * A caller appends turns, builds the next prompt, and locks, unlocks, or edits a stored message.
 * A blank system prompt leaves no system message in a new region.
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-685 docs/specs/common/grok.spec.md
// @aa-sdd PK-286 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-730 docs/specs/common/stages.spec.md
// @aa-sdd PK-287 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-743 docs/specs/common/stages.spec.md
// @aa-sdd PK-745 docs/specs/common/stages.spec.md
// @aa-sdd PK-955 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-637 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-641 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-691 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-692 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-292 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-719 docs/specs/common/server.spec.md
// @aa-sdd PK-293 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-723 docs/specs/common/server.spec.md
// @aa-sdd PK-294 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-701 docs/specs/common/roux.spec.md
// @aa-sdd PK-296 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-297 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-705 docs/specs/common/roux.spec.md
// @aa-sdd PK-298 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-804 docs/specs/common/roux.spec.md
// @aa-sdd PK-806 docs/specs/common/roux.spec.md
// @aa-sdd PK-299 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-300 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-301 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-810 docs/specs/common/roux.spec.md
// @aa-sdd PK-302 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-816 docs/specs/common/roux.spec.md
// @aa-sdd PK-303 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-304 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-821 docs/specs/common/roux.spec.md
// @aa-sdd PK-822 docs/specs/common/roux.spec.md
// @aa-sdd PK-305 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-823 docs/specs/common/roux.spec.md
// @aa-sdd PK-824 docs/specs/common/roux.spec.md
// @aa-sdd PK-306 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-307 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-1385 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-707 docs/specs/common/rust.spec.md
// @aa-sdd PK-708 docs/specs/common/rust.spec.md
// @aa-sdd PK-711 docs/specs/common/rust.spec.md
// @aa-sdd PK-771 docs/specs/common/stages.spec.md
// @aa-sdd PK-643 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-648 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-649 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-725 docs/specs/common/server.spec.md
public class ChatSession {

    private final String conversationId;
    private final String systemPromptText;
    private final RouxChatMemory memory;
    private final boolean restored;
    private final int restoredCount;
    private final UUID regionUuid;
    private boolean active;
    /**
     * The {@code moodLine} field is the console mood text for this SSH session.
     * The empty value means the next prompt carries the system prompt and the turns, without mood text.
     * The opener, the mood line, and the closer are written at the head of one user turn.
     * A transcript of one user turn, or of no user turns, puts that text on the first user turn.
     * A longer transcript puts a new or updated line on the user turn that is being sent.
     * // @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md FINESSE:f-196
     */
    private String moodLine;

    /**
     * The {@code moodHeld} field is the last mood line that was active.
     * Turning that same line back on restores the pinned user turn.
     */
    private String moodHeld;

    /**
     * The {@code moodPinOrdinal} field is the zero-based index of the user turn that carries the mood text.
     * The next append uses this index when the stored user count matches it.
     */
    private int moodPinOrdinal;

    /**
     * The {@code moodMovePending} field records a new or updated mood on a transcript that already holds more than one user turn.
     */
    private boolean moodMovePending;

    /**
     * The {@code headOverAllowance} field is true when the last prompt's pinned turn and locked prefix were over {@code managed-tokens}.
     */
    private boolean headOverAllowance;

    /**
     * The constructor opens a memory-only session and mints a random conversation id.
     * A {@code null} system prompt is stored as the empty string.
     * The session starts active, and it is not marked restored.
     * The region id is {@code null}.
     * {@link #ensureSystemPrompt} writes a non-blank prompt as the first system message.
     * {@link #adoptStoredMood} copies a mood line that is already stored.
     *
     * @param systemPromptText The system prompt from configuration.
     * @param memory The Roux chat memory that stores the messages.
     */
    public ChatSession(String systemPromptText, RouxChatMemory memory) {
        this.conversationId = UUID.randomUUID().toString();
        this.systemPromptText = systemPromptText == null ? "" : systemPromptText;
        this.memory = memory;
        this.active = true;
        this.restored = false;
        this.restoredCount = 0;
        this.regionUuid = null;
        ensureSystemPrompt();
        adoptStoredMood();
    }

    /**
     * The {@code forUser} method opens a disk-backed session for one SSH user.
     * The region must already have been opened with {@link RouxChatMemory#open}.
     * The user name is the Roux region id.
     *
     * @param username The sanitized SSH user name, which is the Roux region id.
     * @param open The open result, which is new or restored.
     * @param systemPromptText The system prompt from configuration.
     * @param memory The Roux chat memory that stores the messages.
     * @return The session for that region.
     */
    public static ChatSession forUser(String username, OpenResult open, String systemPromptText,
            RouxChatMemory memory) {
        return new ChatSession(username, open, systemPromptText, memory);
    }

    /**
     * The constructor stores a disk-backed region.
     * A {@code null} system prompt is stored as the empty string.
     * The session starts active.
     * If {@code open} is {@code null}, the session is not restored, the restored count is zero, and the region id is {@code null}.
     * If {@code open} reports {@code RESTORED}, the session is marked restored.
     * The restored count and the region id come from {@code open} when it is present.
     * {@link #ensureSystemPrompt} and {@link #adoptStoredMood} then run.
     *
     * @param conversationId The Roux region id.
     * @param open The open result, or {@code null}.
     * @param systemPromptText The system prompt from configuration.
     * @param memory The Roux chat memory that stores the messages.
     */
    private ChatSession(String conversationId, OpenResult open, String systemPromptText, RouxChatMemory memory) {
        this.conversationId = conversationId;
        this.systemPromptText = systemPromptText == null ? "" : systemPromptText;
        this.memory = memory;
        this.active = true;
        this.restored = open != null && open.getKind() == OpenResult.RESTORED;
        this.restoredCount = open == null ? 0 : open.getMessageCount();
        this.regionUuid = open == null ? null : open.getRegionUuid();
        ensureSystemPrompt();
        adoptStoredMood();
    }

    /**
     * The {@code getConversationId} method returns the conversation id.
     * The id is the session key.
     * The id is not stored on the messages.
     *
     * @return The Roux and Spring conversation id.
     */
    public String getConversationId() {
        return conversationId;
    }

    /**
     * The {@code getMessages} method returns the full history as message objects.
     *
     * @return The unmarshalled history for this conversation.
     */
    public synchronized List<Message> getMessages() {
        return memory.get(conversationId);
    }

    /**
     * The {@code exportToon} method returns the whole Roux region for this SSH session as a TOON table.
     * TOON is the text table form of the stored messages.
     * If the export is {@code null} or empty, the method returns {@code messages[0]{r,ts,tok,x}:} and a line feed.
     *
     * @return The TOON message table, or the empty table line when the region is empty.
     */
    public synchronized String exportToon() {
        String toon = memory.exportToon(conversationId);
        if (toon == null || toon.length() == 0) {
            return "messages[0]{r,ts,tok,x}:\n";
        }
        return toon;
    }

    /**
     * The {@code isActive} method reports whether chat turns should be sent to the language model.
     * The language model is the program that writes the assistant reply.
     *
     * @return {@code true} when chat turns should be sent.
     */
    public boolean isActive() {
        return active;
    }

    /**
     * The {@code setActive} method stores whether this session may send chat turns.
     *
     * @param active {@code true} when chat mode is eligible, or {@code false} when it is not.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * The {@code reset} method is the {@code chat start} clear.
     * The method drops the stored turns.
     * The mood pin index returns to zero, and a pending mood move is cleared.
     * A configured system prompt is written again.
     * A blank prompt leaves no system message.
     * The session is marked active.
     */
    public synchronized void reset() {
        memory.clear(conversationId);
        moodPinOrdinal = 0;
        moodMovePending = false;
        ensureSystemPrompt();
        active = true;
    }

    /**
     * The {@code stop} method is the {@code chat stop} change.
     * The session is marked inactive.
     * The history is kept.
     */
    public void stop() {
        active = false;
    }

    /**
     * The {@code clearTurns} method is the {@code clear} change.
     * The method drops the stored turns.
     * The mood pin index returns to zero, and a pending mood move is cleared.
     * A configured system prompt is written again.
     * A blank prompt leaves no system message.
     * The active flag is not changed.
     */
    public synchronized void clearTurns() {
        memory.clear(conversationId);
        moodPinOrdinal = 0;
        moodMovePending = false;
        ensureSystemPrompt();
    }

    /**
     * The {@code appendUser} method stores one user turn.
     * A {@code null} text is stored as the empty string.
     * If a mood line is set, no mood move is pending, and the stored user count equals {@code moodPinOrdinal}, the stored text is the mood splice plus the turn.
     * The splice is the opener, the mood line, and the closer written at the head of the turn.
     * Otherwise the turn is stored as given.
     *
     * @param text The user turn.
     */
    public synchronized void appendUser(String text) {
        int ordinal = countStoredUsers();
        String storedText = text == null ? "" : text;
        if (moodLine != null && !moodMovePending && ordinal == moodPinOrdinal) {
            // @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md FINESSE:f-196
            storedText = MoodDesk.spliceTurn(storedText, moodLine);
        }
        List<Message> batch = new ArrayList<Message>();
        batch.add(new UserMessage(storedText));
        memory.add(conversationId, batch);
    }

    /**
     * The {@code appendAssistant} method stores one assistant turn.
     *
     * @param text The assistant turn.
     */
    public synchronized void appendAssistant(String text) {
        List<Message> batch = new ArrayList<Message>();
        batch.add(new AssistantMessage(text));
        memory.add(conversationId, batch);
    }

    /**
     * The {@code replaceTurns} method drops the turns, keeps the configured prompt, and stores the supplied rows.
     * If either list is {@code null}, or the lists differ in length, the method returns {@code false} and changes nothing.
     * The mood line, the held line, the pending move, and the pin index are cleared before the role check.
     * If any role is not {@code user} or {@code assistant}, the method returns {@code false} after those mood fields were cleared.
     * Otherwise {@link #clearTurns} runs, each row is appended, and the method returns {@code true}.
     * A {@code null} text is stored as the empty string.
     *
     * @param roles The role of each row, which must be {@code user} or {@code assistant}.
     * @param texts The text of each row, in the same order and length as {@code roles}.
     * @return {@code true} when the region was replaced, or {@code false} when the lists were rejected.
     */
    public synchronized boolean replaceTurns(List<String> roles, List<String> texts) {
        if (roles == null || texts == null || roles.size() != texts.size()) {
            return false;
        }
        moodLine = null;
        moodHeld = null;
        moodMovePending = false;
        moodPinOrdinal = 0;
        for (int i = 0; i < roles.size(); i++) {
            String role = roles.get(i);
            if (!"user".equals(role) && !"assistant".equals(role)) {
                return false;
            }
        }
        clearTurns();
        for (int i = 0; i < roles.size(); i++) {
            String text = texts.get(i) == null ? "" : texts.get(i);
            if ("assistant".equals(roles.get(i))) {
                appendAssistant(text);
            } else {
                appendUser(text);
            }
        }
        return true;
    }

    /**
     * The {@code buildPrompt} method builds a prompt from the stored system message, a capped history, and the new user text.
     * The new user text is not stored by this call.
     * The token cap passed onward is {@code 0}, which means no token limit.
     * The managed-token allowance and the lock count are also {@code 0}.
     *
     * @param maxMessages The cap on non-system history.
     * @param newUserText The current turn, which is not stored yet.
     * @return The assembled prompt.
     */
    public Prompt buildPrompt(int maxMessages, String newUserText) {
        return buildPrompt(maxMessages, 0, newUserText);
    }

    /**
     * The {@code buildPrompt} method builds a prompt with a message cap and a token cap.
     * A {@code maxTokens} value of {@code 0} means the window is not limited by tokens.
     * The managed-token allowance is {@code 0}, so the sink path is not requested.
     * The lock count is not applied.
     *
     * @param maxMessages The cap on non-system history.
     * @param maxTokens The token cap, or {@code 0} when the cap is unlimited.
     * @param newUserText The live user line, which is not stored yet.
     * @return The assembled prompt.
     */
    public synchronized Prompt buildPrompt(int maxMessages, int maxTokens, String newUserText) {
        return assemblePrompt(maxMessages, maxTokens, 0, false, 0, newUserText);
    }

    /**
     * The {@code buildPrompt} method builds a prompt with a message cap, a token cap, a sink allowance, and a lock count.
     * A {@code maxTokens} value of {@code 0} means the window is not limited by tokens.
     * A {@code managedTokens} value of {@code 0} uses the message window instead of the sink.
     * A positive allowance asks the memory for a sink cut before the window is used.
     * The lock count is applied before the messages are chosen.
     *
     * @param maxMessages The cap on non-system history.
     * @param maxTokens The token cap, or {@code 0} when the cap is unlimited.
     * @param managedTokens The sink allowance, or {@code 0} to use the message window.
     * @param headCount How many non-system turns may lock.
     * @param newUserText The live user line, which is not stored yet.
     * @return The assembled prompt.
     */
    public synchronized Prompt buildPrompt(int maxMessages, int maxTokens, int managedTokens, int headCount,
            String newUserText) {
        return assemblePrompt(maxMessages, maxTokens, managedTokens, true, headCount, newUserText);
    }

    /**
     * The {@code headOverAllowance} method reports the flag stored by the last prompt assembly.
     * The flag is true when the pinned turn and the locked prefix were over the managed-token allowance.
     *
     * @return {@code true} when that last prompt was over the allowance.
     */
    public boolean headOverAllowance() {
        return headOverAllowance;
    }

    /**
     * The {@code lockMessage} method applies the lock count and then locks one stored sequence number.
     * A {@code null} return means the lock was stored.
     * Any other return is the refusal sentence from the memory.
     *
     * @param seq The stored sequence number.
     * @param headCount The configured count of turns that may lock.
     * @return {@code null} when the lock is stored, or one refusal sentence.
     */
    public synchronized String lockMessage(long seq, int headCount) {
        memory.applyHeadCount(conversationId, headCount);
        return memory.lockMessage(conversationId, seq);
    }

    /**
     * The {@code unlockMessage} method applies the lock count and then unlocks one stored sequence number.
     * A {@code null} return means the unlock was stored.
     * Any other return is the refusal sentence from the memory.
     *
     * @param seq The stored sequence number.
     * @param headCount The configured count of turns that may lock.
     * @return {@code null} when the unlock is stored, or one refusal sentence.
     */
    public synchronized String unlockMessage(long seq, int headCount) {
        memory.applyHeadCount(conversationId, headCount);
        return memory.unlockMessage(conversationId, seq);
    }

    /**
     * The {@code editMessage} method replaces the body of one stored message.
     * If {@code text} is {@code null} or empty, the method throws {@code Exception} with the message {@code An empty body is refused.}
     * If the memory result is {@code null} or is not a success, the method throws {@code Exception}.
     * The thrown message is the memory message when that message is non-empty.
     * Otherwise the thrown message is {@code No message seq } plus the sequence number.
     *
     * @param seq The stored sequence number.
     * @param text The new body.
     * @throws Exception The body was empty, or the sequence number was missing.
     */
    public synchronized void editMessage(long seq, String text) throws Exception {
        if (text == null || text.length() == 0) {
            throw new Exception("An empty body is refused.");
        }
        OperationResult result = memory.editMessage(conversationId, seq, text);
        if (result == null || !result.isSuccess()) {
            String message = result == null ? null : result.getMessage();
            throw new Exception(message == null || message.length() == 0 ? "No message seq " + seq : message);
        }
    }

    /**
     * The {@code headMark} method applies the lock count and returns the mark for one sequence number.
     * The memory returns {@code  locked}, {@code  may lock}, or the empty string.
     *
     * @param seq The stored sequence number.
     * @param headCount The configured count of turns that may lock.
     * @return {@code  locked}, {@code  may lock}, or the empty string.
     */
    public synchronized String headMark(long seq, int headCount) {
        memory.applyHeadCount(conversationId, headCount);
        return memory.headMark(conversationId, seq);
    }

    /**
     * The {@code assemblePrompt} method builds the prompt messages and appends the live user line.
     * A {@code null} live line is treated as empty.
     * If {@code applyCount} is {@code true}, the lock count is applied first.
     * If a mood move is pending, a mood line is set, and more than one user turn is stored, the stored mood rows and splices are removed, the pin moves to the current user count, and the live line is appended already spliced.
     * If a mood line is set and the chosen messages contain a system mood marker but no spliced user turn, the live line is appended and {@link MoodDesk} rewrites the mood lines.
     * The system prompt text is passed into that rewrite only when the configured prompt is non-blank.
     * If a mood line is set and neither of those branches ran, {@link #splicePinCopy} updates the pinned copy and the live line is spliced only when the stored user count equals the pin index.
     * If no mood line is set, the live line is appended as given.
     *
     * @param maxMessages The cap on non-system history.
     * @param maxTokens The token cap.
     * @param managedTokens The sink allowance.
     * @param applyCount {@code true} when the lock count is applied before the messages are chosen.
     * @param headCount The configured lock count.
     * @param newUserText The live user line.
     * @return The assembled prompt.
     */
    private Prompt assemblePrompt(int maxMessages, int maxTokens, int managedTokens, boolean applyCount, int headCount,
            String newUserText) {
        String live = newUserText == null ? "" : newUserText;
        if (applyCount) {
            memory.applyHeadCount(conversationId, headCount);
        }
        if (moodLine != null && moodMovePending && countStoredUsers() > 1) {
            // @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md FINESSE:f-196
            dropSystemMoodRows();
            stripStoredSplice();
            moodPinOrdinal = countStoredUsers();
            moodMovePending = false;
            List<Message> promptMessages = windowOrSink(maxMessages, maxTokens, managedTokens, live);
            promptMessages.add(new UserMessage(MoodDesk.spliceTurn(live, moodLine)));
            return new Prompt(promptMessages);
        }
        List<Message> promptMessages = windowOrSink(maxMessages, maxTokens, managedTokens, live);
        if (moodLine != null && systemMoodIn(promptMessages) && !spliceIn(promptMessages)) {
            promptMessages.add(new UserMessage(live));
            String host = promptSeated() ? systemPromptText : "";
            return new Prompt(MoodDesk.DESK.seat(promptMessages, host, moodLine, moodLine));
        }
        if (moodLine != null) {
            splicePinCopy(promptMessages);
            if (countStoredUsers() == moodPinOrdinal) {
                promptMessages.add(new UserMessage(MoodDesk.spliceTurn(live, moodLine)));
            } else {
                promptMessages.add(new UserMessage(live));
            }
            return new Prompt(promptMessages);
        }
        promptMessages.add(new UserMessage(live));
        return new Prompt(promptMessages);
    }

    /**
     * The {@code windowOrSink} method chooses the stored turns that go into the prompt.
     * The live line is not included.
     * The over-allowance flag is cleared first.
     * If {@code managedTokens} is positive, the live line is counted with a heuristic token counter and the memory is asked for a sink cut.
     * If that cut is present, the over-allowance flag follows the cut and the method returns the sink messages.
     * If the allowance is not positive, or the cut is absent, the method returns the message window for the caps.
     *
     * @param maxMessages The cap on non-system history.
     * @param maxTokens The token cap.
     * @param managedTokens The sink allowance.
     * @param live The unsent line, used only to count tokens.
     * @return The stored turns, without the live line.
     */
    private List<Message> windowOrSink(int maxMessages, int maxTokens, int managedTokens, String live) {
        headOverAllowance = false;
        if (managedTokens > 0) {
            int liveTokens = new HeuristicTokenCounter().count(live);
            RouxEngine.SinkCut cut = memory.selectTape(conversationId, managedTokens, liveTokens);
            if (cut != null) {
                headOverAllowance = cut.headOver;
                return memory.sinkMessages(conversationId, cut);
            }
        }
        return memory.getWindow(conversationId, maxMessages, true, maxTokens);
    }

    /**
     * The {@code seatMood} method inserts a console mood after the first system line.
     * A {@code null} message list returns without a change.
     * A {@code null} or blank line, after {@link MoodDesk#normalize}, returns without a change.
     * {@link MoodDesk} rewrites the list.
     * A list that already holds this line between the opener and the closer is left as it is.
     * The rewritten messages replace the contents of {@code messages}.
     *
     * @param messages The prompt, with the newest user turn already appended.
     * @param moodLine The saved mood, or {@code null}.
     */
    static void seatMood(List<Message> messages, String moodLine) {
        if (messages == null) {
            return;
        }
        String line = MoodDesk.normalize(moodLine);
        if (line == null) {
            return;
        }
        List<Message> next = MoodDesk.DESK.seat(messages, "", null, line);
        messages.clear();
        messages.addAll(next);
    }

    /**
     * The {@code unseatMood} method removes every stored mood group from a prompt.
     * A {@code null} list returns without a change.
     * A system prompt ahead of the mood group stays.
     * A leading mood group with no system prompt is removed together with its closer.
     * The rewritten messages replace the contents of {@code messages}.
     *
     * @param messages The prompt to rewrite.
     */
    static void unseatMood(List<Message> messages) {
        if (messages == null) {
            return;
        }
        List<Message> next = MoodDesk.DESK.seat(messages, "", null, null);
        messages.clear();
        messages.addAll(next);
    }

    /**
     * The {@code porchRules} method returns the mood rules text for a pool session.
     * The rules are the opener, the constructed mood, and the closer.
     * That group sits after a system prompt when a system prompt is present.
     * The group sits at the head when the prompt is blank.
     * The system prompt is not included in the returned text.
     * An absent mood group returns empty text.
     *
     * @param messages The prompt or the stored region.
     * @return The rules text, or the empty string.
     */
    public static String porchRules(List<Message> messages) {
        return MoodDesk.DESK.porchRules(messages);
    }

    /**
     * The {@code setMoodLine} method stores or clears the console mood in this Roux region.
     * The line is normalized first.
     * If the normalized line equals the current mood line, including both being absent, the method returns.
     * A {@code null} normalized line removes the splice from stored user turns, keeps the previous line in {@code moodHeld}, and clears the pending move.
     * If there was no current line and the normalized line equals the held line, that line is restored and the splice is written onto the pin when at least one user turn exists.
     * A different new line is stored as both the current line and the held line.
     * If more than one user turn is already stored, the move is left pending and the transcript is not rewritten yet.
     * Otherwise the pin returns to zero, stored system mood rows are dropped, and the splice is written onto the pin.
     * The turns stay.
     *
     * @param line The mood string, or {@code null} to clear it.
     */
    public synchronized void setMoodLine(String line) {
        String next = MoodDesk.normalize(line);
        if (sameText(moodLine, next)) {
            return;
        }
        if (next == null) {
            stripStoredSplice();
            moodHeld = moodLine;
            moodLine = null;
            moodMovePending = false;
            return;
        }
        int turns = countStoredUsers();
        if (moodLine == null && sameText(moodHeld, next)) {
            moodLine = next;
            moodMovePending = false;
            if (turns > 0) {
                writeSpliceOntoPin();
            }
            return;
        }
        moodLine = next;
        moodHeld = next;
        if (turns > 1) {
            // @aa-sdd PK-1084 docs/specs/common/formal-plan.spec.md FINESSE:f-196
            moodMovePending = true;
            return;
        }
        moodMovePending = false;
        moodPinOrdinal = 0;
        dropSystemMoodRows();
        writeSpliceOntoPin();
    }

    /**
     * The {@code adoptStoredMood} method copies a stored splice into {@code moodLine}.
     * If {@code moodLine} is already set, the method returns.
     * A region with no splice leaves the field empty.
     * The transcript is not rewritten.
     * The pin index becomes the zero-based user-turn index whose head matches the adopted line.
     */
    private void adoptStoredMood() {
        if (moodLine != null) {
            return;
        }
        List<Message> stored = memory.get(conversationId);
        moodLine = MoodDesk.DESK.adopt(stored);
        moodHeld = moodLine;
        if (stored == null || moodLine == null) {
            return;
        }
        int ordinal = 0;
        for (int i = 0; i < stored.size(); i++) {
            Message message = stored.get(i);
            if (!(message instanceof UserMessage)) {
                continue;
            }
            if (moodLine.equals(MoodDesk.lineAtTurnHead(message.getText()))) {
                moodPinOrdinal = ordinal;
                return;
            }
            ordinal++;
        }
    }

    /**
     * The {@code countStoredUsers} method counts stored user turns.
     * A {@code null} history returns zero.
     *
     * @return The number of stored user turns.
     */
    private int countStoredUsers() {
        List<Message> stored = memory.get(conversationId);
        int count = 0;
        if (stored == null) {
            return 0;
        }
        for (int i = 0; i < stored.size(); i++) {
            if (stored.get(i) instanceof UserMessage) {
                count++;
            }
        }
        return count;
    }

    /**
     * The {@code sameText} method compares two mood lines.
     * Both {@code null} values are equal.
     * A {@code null} value is not equal to a non-null value.
     *
     * @param left One line.
     * @param right The other line.
     * @return {@code true} when both are absent or both are equal.
     */
    private static boolean sameText(String left, String right) {
        if (left == null) {
            return right == null;
        }
        return left.equals(right);
    }

    /**
     * The {@code dropSystemMoodRows} method removes a stored system mood group.
     * The turns stay.
     * The spliced user turn is written by a later call.
     * The system prompt text is passed in only when the configured prompt is non-blank.
     */
    private void dropSystemMoodRows() {
        List<Message> stored = memory.get(conversationId);
        String host = promptSeated() ? systemPromptText : "";
        List<Message> next = MoodDesk.DESK.seat(stored, host, moodLine, null);
        replaceTranscript(stored, next);
    }

    /**
     * The {@code stripStoredSplice} method removes the mood splice from every stored user turn.
     * A {@code null} history returns without a change.
     * A user turn whose head has a mood line is replaced with the unspliced text.
     * If no turn changes, the transcript is not rewritten.
     */
    private void stripStoredSplice() {
        List<Message> stored = memory.get(conversationId);
        if (stored == null) {
            return;
        }
        ArrayList<Message> next = new ArrayList<Message>();
        boolean changed = false;
        for (int i = 0; i < stored.size(); i++) {
            Message message = stored.get(i);
            if (message instanceof UserMessage && MoodDesk.lineAtTurnHead(message.getText()) != null) {
                next.add(new UserMessage(MoodDesk.unspliceTurn(message.getText())));
                changed = true;
            } else {
                next.add(message);
            }
        }
        if (changed) {
            replaceTranscript(stored, next);
        }
    }

    /**
     * The {@code writeSpliceOntoPin} method writes the current mood line onto the pinned user turn.
     * If no mood line is set, or the history is {@code null}, the method returns.
     * The user turn at {@code moodPinOrdinal} receives the splice.
     * Another user turn that already has a splice loses it.
     * If the pinned turn is missing, the transcript is not rewritten and the next append can write the splice.
     */
    private void writeSpliceOntoPin() {
        if (moodLine == null) {
            return;
        }
        List<Message> stored = memory.get(conversationId);
        if (stored == null) {
            return;
        }
        ArrayList<Message> next = new ArrayList<Message>();
        int seen = 0;
        boolean found = false;
        for (int i = 0; i < stored.size(); i++) {
            Message message = stored.get(i);
            if (!(message instanceof UserMessage)) {
                next.add(message);
                continue;
            }
            if (seen == moodPinOrdinal) {
                next.add(new UserMessage(MoodDesk.spliceTurn(message.getText(), moodLine)));
                found = true;
            } else if (MoodDesk.lineAtTurnHead(message.getText()) != null) {
                next.add(new UserMessage(MoodDesk.unspliceTurn(message.getText())));
            } else {
                next.add(message);
            }
            seen++;
        }
        if (found) {
            replaceTranscript(stored, next);
        }
    }

    /**
     * The {@code splicePinCopy} method writes the mood splice onto the matching user turn in a prompt copy.
     * The match text is the unspliced text of the stored user turn at {@code moodPinOrdinal}.
     * If that stored turn is missing, the method returns.
     * The first prompt message with the same unspliced user text is replaced with the spliced text.
     *
     * @param promptMessages A copy of the prompt messages.
     */
    private void splicePinCopy(List<Message> promptMessages) {
        List<Message> stored = memory.get(conversationId);
        String needle = null;
        int seen = 0;
        if (stored != null) {
            for (int i = 0; i < stored.size(); i++) {
                Message message = stored.get(i);
                if (!(message instanceof UserMessage)) {
                    continue;
                }
                if (seen == moodPinOrdinal) {
                    needle = MoodDesk.unspliceTurn(message.getText());
                    break;
                }
                seen++;
            }
        }
        if (needle == null) {
            return;
        }
        for (int i = 0; i < promptMessages.size(); i++) {
            Message message = promptMessages.get(i);
            if (message instanceof UserMessage
                    && needle.equals(MoodDesk.unspliceTurn(message.getText()))) {
                promptMessages.set(i, new UserMessage(MoodDesk.spliceTurn(message.getText(), moodLine)));
                return;
            }
        }
    }

    /**
     * The {@code systemMoodIn} method reports whether a system message is a mood marker.
     * A {@code null} list returns {@code false}.
     * The markers are {@link MoodDesk#OPENER}, {@link MoodDesk#CLOSER}, and {@link MoodDesk#LEGACY}.
     *
     * @param messages The prompt messages.
     * @return {@code true} when a system opener, closer, or legacy marker is present.
     */
    private static boolean systemMoodIn(List<Message> messages) {
        if (messages == null) {
            return false;
        }
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (!(message instanceof SystemMessage)) {
                continue;
            }
            String text = message.getText();
            if (MoodDesk.OPENER.equals(text) || MoodDesk.CLOSER.equals(text) || MoodDesk.LEGACY.equals(text)) {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code spliceIn} method reports whether a user turn already carries a mood splice.
     * A {@code null} list returns {@code false}.
     *
     * @param messages The prompt messages.
     * @return {@code true} when a user turn already has the splice at its head.
     */
    private static boolean spliceIn(List<Message> messages) {
        if (messages == null) {
            return false;
        }
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message instanceof UserMessage && MoodDesk.lineAtTurnHead(message.getText()) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code replaceTranscript} method replaces the stored messages when they differ.
     * If the memory says a head is designated, the method returns without writing.
     * If {@link #sameTranscript} says the lists already match, the method returns.
     * Otherwise the region is cleared, the replacement is added when it is non-empty, and a checkpoint is written.
     *
     * @param stored The current region.
     * @param next The replacement messages.
     */
    private void replaceTranscript(List<Message> stored, List<Message> next) {
        if (memory.headDesignated(conversationId)) {
            return;
        }
        if (sameTranscript(stored, next)) {
            return;
        }
        memory.clear(conversationId);
        if (next != null && next.size() > 0) {
            memory.add(conversationId, next);
        }
        memory.checkpoint(conversationId);
    }

    /**
     * The {@code sameTranscript} method compares two message lists by type and text.
     * A {@code null} list, or lists of different length, returns {@code false}.
     * A {@code null} message returns {@code false}.
     * A {@code null} text is compared as the empty string.
     *
     * @param left The stored messages.
     * @param right The composed messages.
     * @return {@code true} when every role and every text already match.
     */
    private static boolean sameTranscript(List<Message> left, List<Message> right) {
        if (left == null || right == null || left.size() != right.size()) {
            return false;
        }
        for (int i = 0; i < left.size(); i++) {
            Message a = left.get(i);
            Message b = right.get(i);
            if (a == null || b == null || a.getMessageType() != b.getMessageType()) {
                return false;
            }
            String at = a.getText() == null ? "" : a.getText();
            String bt = b.getText() == null ? "" : b.getText();
            if (!at.equals(bt)) {
                return false;
            }
        }
        return true;
    }

    /**
     * The {@code checkpoint} method asks the memory to persist the hot page for this conversation.
     * The durability rule is the rule the memory already has.
     */
    public void checkpoint() {
        memory.checkpoint(conversationId);
    }

    /**
     * The {@code inspect} method returns the inspect card for one sequence number.
     *
     * @param seq The stored sequence number.
     * @return The inspect card from the memory.
     */
    public org.roux.model.MessageInspect inspect(long seq) {
        return memory.inspect(conversationId, seq);
    }

    /**
     * The {@code inspectById} method returns the inspect card for one identity pair.
     *
     * @param hi The high half of the identity.
     * @param lo The low half of the identity.
     * @return The inspect card from the memory.
     */
    public org.roux.model.MessageInspect inspectById(long hi, long lo) {
        return memory.inspectById(conversationId, hi, lo);
    }

    /**
     * The {@code memoryStatusLine} method returns the Roux status fragment for this conversation.
     *
     * @return The status line from the memory.
     */
    public String memoryStatusLine() {
        return memory.memoryStatusLine(conversationId);
    }

    /**
     * The {@code countByType} method counts stored messages of one Spring message type.
     * {@code SYSTEM}, {@code USER}, and {@code ASSISTANT} map to the matching Roux role.
     * Any other type returns zero.
     *
     * @param type The message type to count.
     * @return The count for that type, or zero when the type is not one of those three.
     */
    public int countByType(MessageType type) {
        if (type == MessageType.SYSTEM) {
            return memory.countByRole(conversationId, Role.SYSTEM);
        }
        if (type == MessageType.USER) {
            return memory.countByRole(conversationId, Role.USER);
        }
        if (type == MessageType.ASSISTANT) {
            return memory.countByRole(conversationId, Role.ASSISTANT);
        }
        return 0;
    }

    /**
     * The {@code getSystemPromptText} method returns the configured system prompt.
     *
     * @return The system prompt text stored by the constructor.
     */
    public String getSystemPromptText() {
        return systemPromptText;
    }

    /**
     * The {@code wasRestored} method reports whether this session restored an existing region.
     *
     * @return {@code true} when the open result said the region was restored.
     */
    public boolean wasRestored() {
        return restored;
    }

    /**
     * The {@code getRestoredCount} method returns how many messages the open result reported.
     *
     * @return The restored message count, or zero when the session was not opened from a result.
     */
    public int getRestoredCount() {
        return restoredCount;
    }

    /**
     * The {@code getRegionUuid} method returns the region id from the open result.
     *
     * @return The region id when the session is disk-backed, or {@code null} for a memory-only session.
     */
    public UUID getRegionUuid() {
        return regionUuid;
    }

    /**
     * The {@code close} method unmaps the Roux region.
     * A disk-backed close keeps the user directory.
     */
    public void close() {
        memory.persistClose(conversationId);
    }

    /**
     * The {@code ensureSystemPrompt} method writes the configured prompt as the first system message on a new region.
     * A blank prompt leaves the system message off.
     * A region that already holds a system message stays as it is.
     */
    private void ensureSystemPrompt() {
        if (!promptSeated()) {
            return;
        }
        if (memory.countByRole(conversationId, Role.SYSTEM) > 0) {
            return;
        }
        List<Message> batch = new ArrayList<Message>();
        batch.add(new SystemMessage(systemPromptText));
        memory.add(conversationId, batch);
    }

    /**
     * The {@code promptSeated} method reports whether a new region receives a system message.
     * The result is true when the configured prompt is non-null and its trimmed text is not empty.
     *
     * @return {@code true} when a new region receives a system message.
     */
    private boolean promptSeated() {
        return systemPromptText != null && systemPromptText.trim().length() > 0;
    }
}
/*****************************************************************************************
 * End of Class: ChatSession.java 082626
 ****************************************************************************************/
