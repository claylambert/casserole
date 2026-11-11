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

package org.casserole.mood;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

// @aa-sdd PK-4161 docs/specs/java/org/aetheris/swing/swing.spec.md
/**
 * The {@code MoodDesk} type is the shared {@link MoodDoor} implementation.
 * The canvas, group, and pipe pushes ask this type for the system lines.
 * The SSH session asks this type to rewrite the stored transcript.
 * The stored transcript is the Roux conversation record.
 * The connect asks this type whether the pane still needs a shell frame.
 * The footer switch gates the canvas push and the shell.
 * The SSH checkbox only makes the shell available.
 * The footer switch, when off, clears a checked SSH shell.
 * The constructed mood sits between {@link #OPENER} and {@link #CLOSER}.
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
 * @author clayl (2026-11-03)
 */
public final class MoodDesk implements MoodDoor {

    /**
     * The field holds the sentence before the constructed mood.
     * The canvas push and the SSH shell use this same line.
     */
    public static final String OPENER =
            "I'm opening a window with this atmosphere\u2026 would you like to step into it with me?";

    /**
     * The field holds the sentence after the constructed mood.
     * The canvas push and the SSH shell use this same line.
     */
    public static final String CLOSER =
            "Shape your replies around this mood \u2014 let it breathe through the words.";

    /**
     * The field holds the activate sentence stored by an older transcript.
     * The later {@link #seat} call still drops that row.
     */
    public static final String LEGACY =
            "Activate the mood above. Use that mood as the shape of your responses.";

    /**
     * The field holds the one shared instance.
     * The callers use this field.
     */
    public static final MoodDesk DESK = new MoodDesk();

    /**
     * The constructor builds the single shared instance.
     * The callers use {@link #DESK}.
     */
    private MoodDesk() {
    }

    /**
     * The {@code normalize} method trims one mood line.
     * The method returns null when {@code line} is null.
     * The method returns null when the trimmed line is empty.
     * The method returns the trimmed line otherwise.
     *
     * @param line The raw mood line.
     * @return The trimmed line, or null when the line is empty.
     */
    public static String normalize(String line) {
        if (line == null) {
            return null;
        }
        String trimmed = line.trim();
        return trimmed.length() == 0 ? null : trimmed;
    }

    /**
     * The {@code canvasSystems} method returns the system texts for a canvas, group, or pipe push.
     * The method returns an empty list when {@code on} is false.
     * The method returns an empty list when {@link #normalize} returns null.
     * The list is {@link #OPENER}, the trimmed line, and {@link #CLOSER} otherwise.
     *
     * @param on Whether the footer switch is on.
     * @param line The saved mood line.
     * @return The opener, the constructed mood, and the closer, or an empty list.
     */
    public static List<String> canvasSystems(boolean on, String line) {
        ArrayList<String> systems = new ArrayList<String>();
        String trimmed = normalize(line);
        if (!on || trimmed == null) {
            return systems;
        }
        // @aa-sdd PK-4179 docs/specs/java/org/aetheris/swing/swing.spec.md
        systems.add(OPENER);
        systems.add(trimmed);
        systems.add(CLOSER);
        return systems;
    }

    /**
     * The {@code moodHead} method joins the opener, the constructed mood, and the closer.
     * The method follows each piece with a newline.
     * The method returns null when {@link #canvasSystems} returns an empty list.
     *
     * @param on Whether the footer switch is on.
     * @param line The saved mood line.
     * @return The stored head, or null when the head is off.
     */
    public static String moodHead(boolean on, String line) {
        List<String> systems = canvasSystems(on, line);
        if (systems.size() == 0) {
            return null;
        }
        return systems.get(0) + "\n" + systems.get(1) + "\n" + systems.get(2) + "\n";
    }

    /**
     * The {@code sshLine} method returns the mood line for that user's panes.
     * The checkbox makes the shell available.
     * The footer switch turns the shell on.
     * The method returns null when {@code armed} is false.
     * The method returns null when {@code on} is false.
     * The method otherwise returns {@link #normalize} of {@code line}.
     *
     * @param armed Whether the checkbox for that user is on.
     * @param on Whether the footer switch is on.
     * @param line The saved mood line.
     * @return The trimmed line, or null when the shell stays clear.
     */
    public static String sshLine(boolean armed, boolean on, String line) {
        if (!armed || !on) {
            return null;
        }
        // @aa-sdd PK-4180 docs/specs/java/org/aetheris/swing/swing.spec.md
        return normalize(line);
    }

    /**
     * The {@code announceBody} method builds the shell frame for this connect or this later call.
     * The {@link #normalize} method trims {@code want} and {@code held}.
     * The method compares a null trimmed value as an empty string.
     * The method returns null when {@code told} is true and those keys are equal.
     * The method returns {@code shell_mood_clear:} and a newline when the wanted line is null.
     * The method returns {@code shell_mood:}, a newline, the wanted line, and a newline otherwise.
     * The pane that has not been told receives the mood, including a clear.
     * The pane that already holds the same line receives nothing.
     *
     * @param told Whether this connection already announced.
     * @param held The line last announced, or empty.
     * @param want The line to announce, or empty to clear.
     * @return The frame body, or null when the pane already matches.
     */
    public static String announceBody(boolean told, String held, String want) {
        String next = normalize(want);
        String have = normalize(held);
        String wantKey = next == null ? "" : next;
        String heldKey = have == null ? "" : have;
        if (told && wantKey.equals(heldKey)) {
            return null;
        }
        if (next == null) {
            return "shell_mood_clear:\n";
        }
        return "shell_mood:\n" + next + "\n";
    }

    /**
     * The {@code parseShell} method reads a mood frame from one channel body.
     * The method returns null when {@code text} is null or empty.
     * The first line ends at the first newline, or at the end when there is no newline.
     * The method removes one trailing carriage return from that first line.
     * The method returns an empty string when the first line starts with {@code shell_mood_clear:}.
     * The method returns null when the first line does not start with {@code shell_mood:}.
     * The method returns an empty string when {@code shell_mood:} has no newline after it.
     * The rest is the text after the first newline.
     * The method removes one trailing line feed from the rest.
     * The method then removes one trailing carriage return.
     * The method returns that rest trimmed, and the trimmed rest may be empty.
     *
     * @param text The stripped channel body.
     * @return The mood line, an empty string when the frame clears, or null when this is not a mood frame.
     */
    public static String parseShell(String text) {
        if (text == null || text.length() == 0) {
            return null;
        }
        int nl = text.indexOf('\n');
        String first = nl < 0 ? text : text.substring(0, nl);
        if (first.endsWith("\r")) {
            first = first.substring(0, first.length() - 1);
        }
        if (first.startsWith("shell_mood_clear:")) {
            return "";
        }
        if (!first.startsWith("shell_mood:")) {
            return null;
        }
        if (nl < 0) {
            return "";
        }
        String rest = text.substring(nl + 1);
        if (rest.endsWith("\n")) {
            rest = rest.substring(0, rest.length() - 1);
        }
        if (rest.endsWith("\r")) {
            rest = rest.substring(0, rest.length() - 1);
        }
        return rest.trim();
    }

    /**
     * The {@code seat} method rebuilds the message list for one mood change.
     * The method returns a new list and does not change {@code stored}.
     * The {@link #normalize} method trims {@code nextLine} and {@code previousLine}.
     * The method treats a null {@code hostPrompt} as an empty string.
     * The prompt is configured when that trimmed text has length greater than zero.
     *
     * The method collects mood texts from the previous line, the next line, and each system message that sits immediately before a closer.
     * The {@link #remember} method skips a null line, skips a line equal to the configured prompt, and keeps the first copy of each other line.
     *
     * The method skips a null message.
     * The method skips a system message whose text is the opener, the closer, or {@link #LEGACY}.
     * The method skips a system message whose next message is the closer or {@link #LEGACY}.
     * The method skips a system message whose text is one of the collected mood lines.
     * The first remaining system message is kept as the prompt message.
     * The other remaining messages go into the body.
     *
     * The result starts with a new system message of the configured prompt when no prompt message was kept and a prompt is configured.
     * The result starts with the kept prompt message when one was found.
     * The result has no leading prompt when none was kept and no prompt is configured.
     * The result appends {@link #OPENER}, the next line, and {@link #CLOSER} as three system messages when the next line is not null.
     * The result appends no triple when the next line is null.
     * The body is appended after that.
     *
     * @param stored The current messages, or null when the list is absent.
     * @param hostPrompt The configured system prompt, or empty when no prompt is configured.
     * @param previousLine The previous mood line, or null.
     * @param nextLine The mood line to store, or null to clear the triple.
     * @return The replacement message list.
     */
    @Override
    public List<Message> seat(List<Message> stored, String hostPrompt, String previousLine, String nextLine) {
        String next = normalize(nextLine);
        String previous = normalize(previousLine);
        String hostText = hostPrompt == null ? "" : hostPrompt.trim();
        boolean hostConfigured = hostText.length() > 0;
        ArrayList<String> moodTexts = new ArrayList<String>();
        remember(moodTexts, previous, hostText, hostConfigured);
        remember(moodTexts, next, hostText, hostConfigured);
        if (stored != null) {
            for (int i = 0; i < stored.size(); i++) {
                Message message = stored.get(i);
                if (!(message instanceof SystemMessage)) {
                    continue;
                }
                if (i + 1 < stored.size() && closes(stored.get(i + 1))) {
                    remember(moodTexts, message.getText(), hostText, hostConfigured);
                }
            }
        }
        Message host = null;
        ArrayList<Message> body = new ArrayList<Message>();
        if (stored != null) {
            for (int i = 0; i < stored.size(); i++) {
                Message message = stored.get(i);
                if (message == null) {
                    continue;
                }
                if (message instanceof SystemMessage && wrapper(message)) {
                    continue;
                }
                if (message instanceof SystemMessage && i + 1 < stored.size() && closes(stored.get(i + 1))) {
                    continue;
                }
                if (message instanceof SystemMessage && moodText(message.getText(), moodTexts, hostText, hostConfigured)) {
                    continue;
                }
                if (host == null && message instanceof SystemMessage) {
                    host = message;
                    continue;
                }
                body.add(message);
            }
        }
        ArrayList<Message> out = new ArrayList<Message>();
        if (host == null) {
            if (hostConfigured) {
                out.add(new SystemMessage(hostText));
            }
        } else {
            out.add(host);
        }
        if (next != null) {
            // @aa-sdd PK-4181 docs/specs/java/org/aetheris/swing/swing.spec.md
            out.add(new SystemMessage(OPENER));
            out.add(new SystemMessage(next));
            out.add(new SystemMessage(CLOSER));
        }
        out.addAll(body);
        return out;
    }

    /**
     * The {@code adopt} method reads the constructed mood already stored.
     * The method returns null when {@code stored} is null.
     * The method finds the first system message.
     * The {@link #seatedLine} call at that index returns the mood when the triple or the legacy pair matches.
     * The {@link #seatedLine} call at the next index is used when the first index does not match.
     * The method then scans each user message with {@link #lineAtTurnHead}.
     * The method returns null when no mood is found.
     *
     * @param stored The current messages, or null when the list is absent.
     * @return The trimmed mood line, or null when no mood is stored.
     */
    @Override
    public String adopt(List<Message> stored) {
        if (stored == null) {
            return null;
        }
        int first = -1;
        for (int i = 0; i < stored.size(); i++) {
            if (stored.get(i) instanceof SystemMessage) {
                first = i;
                break;
            }
        }
        if (first >= 0) {
            String atHead = seatedLine(stored, first);
            if (atHead != null) {
                return atHead;
            }
            String beside = seatedLine(stored, first + 1);
            if (beside != null) {
                return beside;
            }
        }
        // @aa-sdd PK-4236 docs/specs/java/org/aetheris/swing/swing.spec.md
        for (int i = 0; i < stored.size(); i++) {
            Message message = stored.get(i);
            if (message instanceof UserMessage) {
                String line = lineAtTurnHead(message.getText());
                if (line != null) {
                    return line;
                }
            }
        }
        return null;
    }

    /**
     * The {@code porchRules} method returns the mood rules text for a prompt or a stored region.
     * The method returns an empty string when {@code messages} is null.
     * The method finds the first system message.
     * The method returns {@link #pairText} at that index when its length is greater than zero.
     * The method returns {@link #pairText} at the next index when that text has length greater than zero.
     * The {@link #rulesFromTurn} method supplies the text when neither pair has text.
     * The configured system prompt stays out of the returned text.
     * The method returns an empty string when no pair is present.
     *
     * @param messages The prompt messages or the stored region.
     * @return The rules text, or an empty string.
     */
    @Override
    public String porchRules(List<Message> messages) {
        if (messages == null) {
            return "";
        }
        int host = -1;
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (message instanceof SystemMessage) {
                host = i;
                break;
            }
        }
        if (host >= 0) {
            String leading = pairText(messages, host);
            if (leading.length() > 0) {
                return leading;
            }
            String beside = pairText(messages, host + 1);
            if (beside.length() > 0) {
                return beside;
            }
        }
        // @aa-sdd PK-4237 docs/specs/java/org/aetheris/swing/swing.spec.md
        return rulesFromTurn(messages);
    }

    /**
     * The {@code spliceTurn} method writes the invitation at the head of a turn, then the turn text.
     * The head is the mood label, the line, the shape label, {@link #OPENER}, and {@link #CLOSER}.
     * The line is not repeated between the two sentences.
     * The {@link #unspliceTurn} method removes a stored head first, including a head in the older order.
     * The method returns that stripped turn when {@link #normalize} of {@code line} is null.
     * The method returns the invitation plus that stripped turn otherwise.
     * // @aa-sdd PK-4250 docs/specs/java/org/aetheris/swing/swing.spec.md
     *
     * @param turn The turn text. The method treats a null turn as an empty string after the head is removed.
     * @param line The mood line, or null when the splice should clear.
     * @return The turn the prompt sends.
     */
    public static String spliceTurn(String turn, String line) {
        String next = normalize(line);
        String raw = unspliceTurn(turn);
        if (next == null) {
            return raw;
        }
        return inviteHead(next) + raw;
    }

    /**
     * The {@code unspliceTurn} method removes one spliced mood from the head of a turn.
     * The method returns an empty string when {@code turn} is null.
     * The method returns the turn unchanged when {@link #lineAtTurnHead} finds no mood.
     * The method removes a head written by {@link #inviteHead} when the turn starts with that head.
     * The method removes an older head of {@link #OPENER}, the line, and {@link #CLOSER} when the turn starts with that older head.
     * The method returns the turn unchanged when neither head matches the start.
     *
     * @param turn The prompt turn to strip.
     * @return The turn with one spliced mood removed.
     */
    public static String unspliceTurn(String turn) {
        String line = lineAtTurnHead(turn);
        if (line == null || turn == null) {
            return turn == null ? "" : turn;
        }
        String fresh = inviteHead(line);
        if (turn.startsWith(fresh)) {
            return turn.substring(fresh.length());
        }
        String older = OPENER + "\n" + line + "\n" + CLOSER + "\n";
        if (turn.startsWith(older)) {
            return turn.substring(older.length());
        }
        return turn;
    }

    /**
     * The {@code lineAtTurnHead} method returns the mood at the head of a turn.
     * The method returns null when {@code turn} is null.
     * The {@link #invitedLine} method is tried first.
     * The {@link #olderSpliceLine} method is tried when the invitation form does not match.
     * The method returns null when neither form matches.
     *
     * @param turn The prompt turn to read.
     * @return The mood at the head of the turn, or null when no head matches.
     */
    public static String lineAtTurnHead(String turn) {
        if (turn == null) {
            return null;
        }
        String invited = invitedLine(turn);
        if (invited != null) {
            return invited;
        }
        return olderSpliceLine(turn);
    }

    /**
     * The {@code inviteHead} method builds the head a splice writes.
     * The text is {@code mood:}, the line, {@code shape:}, {@link #OPENER}, {@link #CLOSER}, and a newline.
     *
     * @param line The normalized mood line.
     * @return The head a splice writes.
     */
    private static String inviteHead(String line) {
        return "mood: " + line + "\nshape:\n" + OPENER + "\n" + CLOSER + "\n";
    }

    /**
     * The {@code invitedLine} method returns the mood when the head is the invitation.
     * The method returns null when the turn does not start with {@code mood: }.
     * The method returns null when that label has no newline after it.
     * The method returns null when the text between the label and the newline trims to empty.
     * The text after that newline must start with {@code shape:}, a newline, {@link #OPENER}, a newline, and {@link #CLOSER}.
     * The text may end there or continue after one more newline.
     * The method returns null when the text does not match.
     * The method returns the normalized mood line when the text matches.
     *
     * @param turn The prompt turn to read.
     * @return The mood when the head is the invitation, or null when it is not.
     */
    private static String invitedLine(String turn) {
        String label = "mood: ";
        if (!turn.startsWith(label)) {
            return null;
        }
        int lineEnd = turn.indexOf('\n');
        if (lineEnd < 0) {
            return null;
        }
        String line = normalize(turn.substring(label.length(), lineEnd));
        if (line == null) {
            return null;
        }
        String rest = turn.substring(lineEnd + 1);
        String shape = "shape:\n" + OPENER + "\n" + CLOSER;
        if (!rest.startsWith(shape + "\n") && !rest.equals(shape)) {
            return null;
        }
        return line;
    }

    /**
     * The {@code olderSpliceLine} method returns the mood when the head is still the opener, the line, and the closer.
     * The method returns null when the turn does not start with {@link #OPENER} and a newline.
     * The method returns null when no newline follows that prefix.
     * The method returns null when the text between those newlines trims to empty.
     * The text after the line must start with {@link #CLOSER} and a newline, or equal {@link #CLOSER}.
     * The method returns null when that text does not match.
     * The method returns the normalized mood line when the text matches.
     *
     * @param turn The prompt turn to read.
     * @return The mood when the head is the older splice, or null when it is not.
     */
    private static String olderSpliceLine(String turn) {
        String prefix = OPENER + "\n";
        if (!turn.startsWith(prefix)) {
            return null;
        }
        int lineEnd = turn.indexOf('\n', prefix.length());
        if (lineEnd < 0) {
            return null;
        }
        String line = normalize(turn.substring(prefix.length(), lineEnd));
        if (line == null) {
            return null;
        }
        String rest = turn.substring(lineEnd + 1);
        if (!rest.startsWith(CLOSER + "\n") && !rest.equals(CLOSER)) {
            return null;
        }
        return line;
    }

    /**
     * The {@code rulesFromTurn} method reads mood rules from the first spliced user turn.
     * The method uses the first user message whose {@link #lineAtTurnHead} is not null.
     * The returned text is {@link #inviteHead} of that line without its last character.
     * The method returns an empty string when no user turn has a spliced head.
     *
     * @param messages The prompt messages to scan.
     * @return The rules text from a spliced turn, or an empty string.
     */
    private static String rulesFromTurn(List<Message> messages) {
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            if (!(message instanceof UserMessage)) {
                continue;
            }
            String line = lineAtTurnHead(message.getText());
            if (line != null) {
                // @aa-sdd PK-4251 docs/specs/java/org/aetheris/swing/swing.spec.md
                String head = inviteHead(line);
                return head.substring(0, head.length() - 1);
            }
        }
        return "";
    }

    /**
     * The {@code pairText} method returns the rules text at one message index.
     * The method returns {@link #tripleText} when that text has length greater than zero.
     * The method returns {@link #legacyText} otherwise.
     *
     * @param messages The prompt messages to read.
     * @param at The index of the mood system line.
     * @return The rules text, or an empty string.
     */
    private static String pairText(List<Message> messages, int at) {
        String triple = tripleText(messages, at);
        if (triple.length() > 0) {
            return triple;
        }
        return legacyText(messages, at);
    }

    /**
     * The {@code tripleText} method joins one opener, mood, and closer into rules text.
     * The method returns an empty string when index {@code at} is not {@link #OPENER}.
     * The method returns an empty string when the message two rows later is not {@link #CLOSER}.
     * The method returns an empty string when the middle text trims to empty or is a wrapper sentence.
     * The method returns {@link #OPENER}, a blank line, the mood, a blank line, and {@link #CLOSER} otherwise.
     *
     * @param messages The prompt messages to read.
     * @param at The index of the opener.
     * @return The rules text, or an empty string.
     */
    private static String tripleText(List<Message> messages, int at) {
        if (!systemText(messages, at, OPENER) || !systemText(messages, at + 2, CLOSER)) {
            return "";
        }
        String line = normalize(messages.get(at + 1).getText());
        if (line == null || wrapperText(line)) {
            return "";
        }
        return OPENER + "\n\n" + line + "\n\n" + CLOSER;
    }

    /**
     * The {@code legacyText} method joins one older mood row and {@link #LEGACY} into rules text.
     * The method returns an empty string when the next message is not {@link #LEGACY}.
     * The method returns an empty string when the mood text trims to empty or is a wrapper sentence.
     * The method returns the mood, a blank line, and {@link #LEGACY} otherwise.
     *
     * @param messages The prompt messages to read.
     * @param at The index of the stored mood row.
     * @return The rules text, or an empty string.
     */
    private static String legacyText(List<Message> messages, int at) {
        if (!systemText(messages, at + 1, LEGACY)) {
            return "";
        }
        String line = normalize(messages.get(at).getText());
        if (line == null || wrapperText(line)) {
            return "";
        }
        return line + "\n\n" + LEGACY;
    }

    /**
     * The {@code seatedLine} method returns the constructed mood at one index.
     * The method returns {@link #moodOfTriple} when that call finds a triple.
     * The method returns {@link #moodOfLegacy} otherwise.
     *
     * @param stored The messages to read.
     * @param index The candidate opener, or a legacy mood row.
     * @return The constructed mood, or null when neither form matches.
     */
    private static String seatedLine(List<Message> stored, int index) {
        String triple = moodOfTriple(stored, index);
        if (triple != null) {
            return triple;
        }
        return moodOfLegacy(stored, index);
    }

    /**
     * The {@code moodOfTriple} method returns the mood between {@link #OPENER} and {@link #CLOSER}.
     * The method returns null when {@code index} is not {@link #OPENER}.
     * The method returns null when the message two rows later is not {@link #CLOSER}.
     * The method returns null when the middle text trims to empty or is a wrapper sentence.
     * The method returns the trimmed middle text otherwise.
     *
     * @param stored The messages to read.
     * @param index The candidate opener.
     * @return The constructed mood, or null when the triple does not match.
     */
    private static String moodOfTriple(List<Message> stored, int index) {
        if (!systemText(stored, index, OPENER) || !systemText(stored, index + 2, CLOSER)) {
            return null;
        }
        String line = normalize(stored.get(index + 1).getText());
        if (line == null || wrapperText(line)) {
            return null;
        }
        return line;
    }

    /**
     * The {@code moodOfLegacy} method returns the mood stored before {@link #LEGACY}.
     * The method returns null when the next message is not {@link #LEGACY}.
     * The method returns null when the mood text trims to empty or is a wrapper sentence.
     * The method returns the trimmed mood text otherwise.
     *
     * @param stored The messages to read.
     * @param index The candidate mood row.
     * @return The constructed mood, or null when the legacy pair does not match.
     */
    private static String moodOfLegacy(List<Message> stored, int index) {
        if (!systemText(stored, index + 1, LEGACY)) {
            return null;
        }
        String line = normalize(stored.get(index).getText());
        if (line == null || wrapperText(line)) {
            return null;
        }
        return line;
    }

    /**
     * The {@code systemText} method reports whether one system row holds an exact text.
     * The method returns false when {@code messages} is null.
     * The method returns false when {@code index} is outside the list.
     * The method returns false when the row is not a system message.
     * The method returns true when {@code text} equals that row.
     * The method returns false when the texts differ.
     *
     * @param messages The messages to read.
     * @param index The candidate row.
     * @param text The exact text to match.
     * @return Whether that system row holds the text.
     */
    private static boolean systemText(List<Message> messages, int index, String text) {
        if (messages == null || index < 0 || index >= messages.size()) {
            return false;
        }
        Message message = messages.get(index);
        return message instanceof SystemMessage && text.equals(message.getText());
    }

    /**
     * The {@code wrapper} method reports whether this row is an opener, a closer, or {@link #LEGACY}.
     * The method returns true only for a system message whose text {@link #wrapperText} reports as a wrapper sentence.
     * The method returns false otherwise.
     *
     * @param message The candidate message.
     * @return Whether this row is an opener, a closer, or the old activate sentence.
     */
    private static boolean wrapper(Message message) {
        return message instanceof SystemMessage && wrapperText(message.getText());
    }

    /**
     * The {@code closes} method reports whether the next row closes a mood.
     * The method returns false when the message is not a system message.
     * The method returns true when the text equals {@link #CLOSER} or {@link #LEGACY}.
     *
     * @param message The candidate message.
     * @return Whether the row ahead of a mood is the closer or the old activate sentence.
     */
    private static boolean closes(Message message) {
        if (!(message instanceof SystemMessage)) {
            return false;
        }
        String text = message.getText();
        return CLOSER.equals(text) || LEGACY.equals(text);
    }

    /**
     * The {@code wrapperText} method reports whether this text is an opener, a closer, or {@link #LEGACY}.
     * The match is an exact {@code equals} against {@link #OPENER}, {@link #CLOSER}, or {@link #LEGACY}.
     *
     * @param text The candidate text.
     * @return Whether this text is an opener, a closer, or the old activate sentence.
     */
    private static boolean wrapperText(String text) {
        return OPENER.equals(text) || CLOSER.equals(text) || LEGACY.equals(text);
    }

    /**
     * The {@code remember} method keeps one mood string when it is new.
     * The {@link #normalize} method trims {@code text}.
     * The method returns when that trim is null.
     * The method returns when a system prompt is configured and the line equals {@code hostText}.
     * The method adds the line when the list does not already contain it.
     *
     * @param lines The known mood strings.
     * @param text The candidate text.
     * @param hostText The configured system prompt.
     * @param hostConfigured Whether a system prompt is configured.
     */
    private static void remember(List<String> lines, String text, String hostText, boolean hostConfigured) {
        String line = normalize(text);
        if (line == null) {
            return;
        }
        if (hostConfigured && hostText.equals(line)) {
            return;
        }
        if (!lines.contains(line)) {
            lines.add(line);
        }
    }

    /**
     * The {@code moodText} method reports whether this system row is a collected mood row.
     * The {@link #normalize} method trims {@code text}.
     * The method returns false when that trim is null.
     * The method returns false when a system prompt is configured and the line equals {@code hostText}.
     * The method returns whether {@code lines} contains the trimmed text otherwise.
     *
     * @param text The system text to test.
     * @param lines The known mood strings.
     * @param hostText The configured system prompt.
     * @param hostConfigured Whether a system prompt is configured.
     * @return Whether this system row is a mood row.
     */
    private static boolean moodText(String text, List<String> lines, String hostText, boolean hostConfigured) {
        String line = normalize(text);
        if (line == null) {
            return false;
        }
        if (hostConfigured && hostText.equals(line)) {
            return false;
        }
        return lines.contains(line);
    }
}
