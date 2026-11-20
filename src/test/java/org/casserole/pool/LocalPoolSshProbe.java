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

package org.casserole.pool;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.sshd.client.SshClient;
import org.apache.sshd.client.channel.ChannelShell;
import org.apache.sshd.client.channel.ClientChannelEvent;
import org.apache.sshd.client.keyverifier.AcceptAllServerKeyVerifier;
import org.apache.sshd.client.session.ClientSession;
import org.casserole.llm.LlamaCppLlmService;
import org.roux.RouxEngine;
import org.roux.model.Role;
import org.roux.token.HeuristicTokenCounter;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;

/**
 * The class opens one SSH shell to a running Casserole and sends one short line.
 * It is a forensics probe. It is not part of the test suite. Casserole must already be listening on port {@code 2222}.
 * The channel is a shell. It does not request a PTY. The password is read from {@code users.yaml} and is not printed.
 * The run waits up to {@link #HOLD_MS} and writes the timing to standard out. The second argument {@code tool} asks for a shell command.
 * The second argument {@code llama} reads status first. A family other than {@code llama-openapi} does not send a chat line. A login that is already open returns without a chat line.
 * The second argument {@code slot} does not open SSH. It times the llama slot fit against port {@code 3333}. The third argument is the pair count. A missing pair count uses {@code 99}.
 * Any other argument asks for one word and no tools. The wait ends when the prompt returns.
 */
public class LocalPoolSshProbe {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 2222;
    private static final String LLAMA_BASE = "http://127.0.0.1:3333";
    private static final long HOLD_MS = 120_000L;
    private static final long LLAMA_HOLD_MS = 180_000L;
    private static final long SLOT_BUDGET_MS = 90_000L;
    private static final String PLAIN = "Reply with the single word pong. Do not use tools.";
    private static final String TOOL = "Run a shell command that prints the single word pong. Then reply with that word.";
    private static final String LOCKED = "The locked turns do not fit the slot.";

    /**
     * The method runs one probe.
     * The first argument is the SSH user. A missing argument uses {@code alice}. The second argument selects the mode. {@code slot} ignores the user and times the llama fit. Any other mode opens one shell.
     *
     * @param args The args are the optional user name, the optional mode, and the optional pair count.
     * @throws Exception The method throws this exception when the SSH client cannot open the shell.
     */
    public static void main(String[] args) throws Exception {
        String user = args.length > 0 ? args[0] : "alice";
        String mode = args.length > 1 ? args[1] : "plain";
        int code;
        if ("slot".equals(mode)) {
            int pairs = 99;
            if (args.length > 2) {
                try {
                    pairs = Integer.parseInt(args[2]);
                } catch (NumberFormatException ex) {
                    pairs = 99;
                }
            }
            code = new LocalPoolSshProbe().runSlot(pairs);
        } else {
            code = new LocalPoolSshProbe().run(user, mode);
        }
        System.exit(code);
    }

    /**
     * The method logs in, sends one line, and records when the pane answers.
     * A missing welcome returns {@code 1}. A session that is already open returns {@code 5}. A {@code pool down} line returns {@code 2}. An empty reply returns {@code 3}. A turn that does not return returns {@code 4}. A finished reply returns {@code 0}.
     * {@code llama} reads {@code status} before the chat line. A family other than {@code llama-openapi} returns {@code 1} and does not send the line.
     *
     * @param user The user is the SSH login name.
     * @param mode The mode selects the line. {@code tool} asks for a shell command. {@code llama} checks the family first.
     * @return The return value is the process status.
     * @throws Exception The method throws this exception when the SSH client cannot open the shell.
     */
    public int run(String user, String mode) throws Exception {
        boolean llama = "llama".equals(mode);
        String line = "tool".equals(mode) ? TOOL : PLAIN;
        String password = passwordFor(user);
        if (password.length() == 0) {
            System.out.println("probe no password for " + user);
            return 1;
        }
        long opened = System.currentTimeMillis();
        SshClient client = SshClient.setUpDefaultClient();
        client.setServerKeyVerifier(AcceptAllServerKeyVerifier.INSTANCE);
        client.start();
        try (ClientSession session = client.connect(user, HOST, PORT).verify(15, TimeUnit.SECONDS).getSession()) {
            session.addPasswordIdentity(password);
            session.auth().verify(15, TimeUnit.SECONDS);
            long authed = System.currentTimeMillis();
            ChannelShell shell = session.createShellChannel();
            shell.setUsePty(false);
            ByteArrayOutputStream captured = new ByteArrayOutputStream();
            shell.setOut(captured);
            shell.setErr(captured);
            shell.open().verify(15, TimeUnit.SECONDS);
            long welcomeAt = waitOpen(shell, captured, "Welcome to Casserole chat", 15_000L);
            String early = strip(captured.toString(StandardCharsets.UTF_8));
            if (early.contains("session already open")) {
                System.out.println("probe user=" + user + " mode=" + modeLabel(mode) + " sessionBusy=true");
                System.out.println("probe early=" + oneLine(early));
                return 5;
            }
            if (!shell.isOpen() || welcomeAt < 0) {
                System.out.println("probe no welcome bytes=" + captured.size());
                System.out.println("probe early=" + oneLine(early));
                return 1;
            }
            if (waitForCount(captured, "chat> ", 1, 15_000L) < 0) {
                System.out.println("probe no prompt bytes=" + captured.size());
                return 1;
            }
            OutputStream stdin = shell.getInvertedIn();
            if (llama) {
                int seated = prepareLlama(stdin, captured);
                if (seated != 0) {
                    return seated;
                }
            }
            int prompts = countOf(captured, "chat> ");
            stdin.write((line + "\r").getBytes(StandardCharsets.UTF_8));
            stdin.flush();
            long sent = System.currentTimeMillis();
            long hold = llama ? LLAMA_HOLD_MS : HOLD_MS;
            long doneAt = waitForCount(captured, "chat> ", prompts + 1, hold);
            long closed = System.currentTimeMillis();
            shell.waitFor(EnumSet.of(ClientChannelEvent.CLOSED), 200L);
            String text = strip(captured.toString(StandardCharsets.UTF_8));
            boolean poolDown = text.contains("pool down");
            boolean empty = text.contains("The engine returned an empty reply.");
            int headerAt = text.indexOf(" · ");
            boolean header = headerAt >= 0;
            String reply = header ? text.substring(headerAt) : "";
            System.out.println("probe user=" + user
                    + " mode=" + modeLabel(mode)
                    + " authMs=" + (authed - opened)
                    + " welcomeMs=" + (welcomeAt - opened)
                    + " sentMs=" + (sent - opened)
                    + " replyMs=" + (doneAt < 0 ? "none" : Long.toString(doneAt - sent))
                    + " holdMs=" + (closed - sent)
                    + " bytes=" + captured.size()
                    + " hasPoolDown=" + poolDown
                    + " hasEmpty=" + empty
                    + " hasHeader=" + header
                    + " hasPong=" + reply.toLowerCase().contains("pong"));
            printSemantics(text);
            System.out.println("probe tail=" + tail(text, 500));
            if (poolDown) {
                return 2;
            }
            if (empty) {
                return 3;
            }
            if (doneAt >= 0 && header) {
                return 0;
            }
            return 4;
        } finally {
            client.stop();
        }
    }

    /**
     * The method reads status and returns to chat when the family is {@code llama-openapi}.
     * A missing command prompt returns {@code 1}. A different family returns {@code 1} and does not send a chat line.
     *
     * @param stdin The stream is the shell input.
     * @param captured The buffer is the shell output.
     * @return The return value is {@code 0} when the chat line may be sent.
     * @throws Exception The method throws this exception when the shell write fails.
     */
    private int prepareLlama(OutputStream stdin, ByteArrayOutputStream captured) throws Exception {
        stdin.write(new byte[] { 0x1b });
        stdin.flush();
        if (waitFor(captured, "cmd> ", 5_000L) < 0) {
            System.out.println("probe no command prompt");
            return 1;
        }
        int commands = countOf(captured, "cmd> ");
        stdin.write("status\r".getBytes(StandardCharsets.UTF_8));
        stdin.flush();
        if (waitForCount(captured, "cmd> ", commands + 1, 15_000L) < 0) {
            System.out.println("probe no status return");
            return 1;
        }
        String text = strip(captured.toString(StandardCharsets.UTF_8));
        String family = valueAfter(text, "family:");
        String messages = valueAfter(text, "messages:");
        String restored = text.contains("New session") ? "new" : valueAfter(text, "Restored");
        System.out.println("probe family=" + family + " messages=" + messages + " restored=" + restored);
        if (!"llama-openapi".equals(family)) {
            System.out.println("probe family is not llama-openapi; chat line not sent");
            return 1;
        }
        int prompts = countOf(captured, "chat> ");
        stdin.write(new byte[] { 0x1b });
        stdin.flush();
        if (waitForCount(captured, "chat> ", prompts + 1, 5_000L) < 0) {
            System.out.println("probe no return to chat");
            return 1;
        }
        return 0;
    }

    /**
     * The method times one llama slot fit and does not open SSH.
     * A one-message render is the ping. The saturated list is one system line, the pair count of short turns, and one live line.
     * The allowance is {@code num-ctx} minus {@code num-predict} from the llama-openapi block. A missing positive {@code num-ctx} uses {@code 4096}. A missing positive {@code num-predict} leaves the reserve at {@code 0}.
     * The first render uses the product timeout of 2 seconds. A miss is retried once at 15 seconds so a slow count is visible. The drop loop uses the 2 second timeout.
     * A server that misses the ping returns {@code 2}. A fit inside the budget returns {@code 0}. A locked prefix that stays over returns {@code 3}. A loop that is still over at the budget returns {@code 4}.
     * The method does not post a completion. The method does not print the API key.
     *
     * @param pairs The pair count is the number of stored user and assistant turns. A count below {@code 1} becomes {@code 1}. A count above {@code 99} becomes {@code 99}.
     * @return The return value is the process status.
     * @throws Exception The method throws this exception when the request body cannot be built.
     */
    public int runSlot(int pairs) throws Exception {
        if (pairs < 1) {
            pairs = 1;
        }
        if (pairs > 99) {
            pairs = 99;
        }
        int allowance = llamaAllowance();
        HttpClient http = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(2))
                .followRedirects(HttpClient.Redirect.NEVER)
                .build();
        ArrayList<Message> ping = new ArrayList<Message>();
        ping.add(new UserMessage("pong"));
        long pingStart = System.currentTimeMillis();
        int pingTokens = rendered(http, ping, 2);
        long pingMs = System.currentTimeMillis() - pingStart;
        System.out.println("slot pingMs=" + pingMs + " pingTokens=" + pingTokens + " allowance=" + allowance);
        if (pingTokens < 0) {
            System.out.println("slot ping miss; llama server did not answer within 2s");
            return 2;
        }
        ArrayList<Message> messages = saturated(pairs);
        long fullStart = System.currentTimeMillis();
        int full = rendered(http, messages, 2);
        long fullMs = System.currentTimeMillis() - fullStart;
        int retry = -1;
        long retryMs = 0L;
        if (full < 0) {
            long retryStart = System.currentTimeMillis();
            retry = rendered(http, messages, 15);
            retryMs = System.currentTimeMillis() - retryStart;
        }
        int heuristic = 0;
        HeuristicTokenCounter counter = new HeuristicTokenCounter();
        for (int i = 0; i < messages.size(); i++) {
            Message message = messages.get(i);
            String text = message == null || message.getText() == null ? "" : message.getText();
            heuristic = heuristic + counter.count(text);
        }
        System.out.println("slot messages=" + messages.size()
                + " pairs=" + pairs
                + " heuristic=" + heuristic
                + " fullTokens=" + full
                + " fullMs=" + fullMs
                + " retryTokens=" + retry
                + " retryMs=" + retryMs
                + " allowance=" + allowance);
        if (full < 0) {
            System.out.println("slot firstMiss=true productWouldLeaveUnsent=true");
            return retry < 0 ? 2 : 4;
        }
        if (full <= allowance) {
            System.out.println("slot alreadyFits=true drops=0");
            return 0;
        }
        return dropUntilFit(http, messages, allowance, full);
    }

    /**
     * The method corrects the weights from the rendered total and walks twice at most.
     * A render miss is tried once more. A second miss leaves the prompt unsent.
     * A kept list that is still over after the second walk stops with the locked-slot sentence.
     *
     * @param http The client is the slot client.
     * @param messages The messages are the saturated prompt.
     * @param allowance The allowance is the slot.
     * @param full The full count is the first rendered count.
     * @return The return value is the process status.
     * @throws Exception The method throws this exception when the request body cannot be built.
     */
    private int dropUntilFit(HttpClient http, ArrayList<Message> messages, int allowance, int full) throws Exception {
        int count = messages.size();
        byte[] roles = new byte[count];
        int[] weights = new int[count];
        HeuristicTokenCounter counter = new HeuristicTokenCounter();
        int liveAt = -1;
        for (int i = 0; i < count; i++) {
            Message message = messages.get(i);
            String text = message == null || message.getText() == null ? "" : message.getText();
            if (message instanceof SystemMessage) {
                roles[i] = Role.SYSTEM;
            } else if (message instanceof AssistantMessage) {
                roles[i] = Role.ASSISTANT;
            } else {
                roles[i] = Role.USER;
            }
            weights[i] = counter.count(text);
            if (message instanceof UserMessage) {
                liveAt = i;
            }
        }
        boolean[] include = new boolean[count];
        for (int i = 0; i < count; i++) {
            include[i] = true;
        }
        spreadProbe(weights, include, full);
        boolean[] omit = new boolean[count];
        int last = full;
        long started = System.currentTimeMillis();
        for (int pass = 0; pass < 2; pass++) {
            int kept = 0;
            for (int i = 0; i < count; i++) {
                if (!omit[i]) {
                    kept++;
                }
            }
            if (kept == 0) {
                System.out.println("slot sentence=" + LOCKED + " pass=" + pass);
                return 3;
            }
            long[] seqs = new long[kept];
            byte[] roleCut = new byte[kept];
            int[] tokens = new int[kept];
            boolean[] lives = new boolean[kept];
            int[] origin = new int[kept];
            int at = 0;
            for (int i = 0; i < count; i++) {
                if (omit[i]) {
                    continue;
                }
                origin[at] = i;
                seqs[at] = i == liveAt ? 0L : (long) i + 1L;
                roleCut[at] = roles[i];
                tokens[at] = weights[i];
                lives[at] = i == liveAt;
                at++;
            }
            long selectStart = System.currentTimeMillis();
            RouxEngine.SinkCut cut = RouxEngine.selectSlot(allowance, seqs, roleCut, tokens, null, null, lives);
            long selectMs = System.currentTimeMillis() - selectStart;
            if (cut == null || cut.ordinals == null || cut.ordinals.length == 0) {
                System.out.println("slot sentence=" + LOCKED + " pass=" + pass + " selectMs=" + selectMs);
                return 3;
            }
            ArrayList<Message> chosen = new ArrayList<Message>();
            boolean[] seen = new boolean[count];
            for (int i = 0; i < cut.ordinals.length; i++) {
                int compact = cut.ordinals[i];
                if (compact < 0 || compact >= origin.length) {
                    continue;
                }
                int index = origin[compact];
                seen[index] = true;
                chosen.add(messages.get(index));
            }
            if (chosen.size() == 0) {
                System.out.println("slot sentence=" + LOCKED + " pass=" + pass);
                return 3;
            }
            long renderStart = System.currentTimeMillis();
            int rendered = rendered(http, chosen, 2);
            long renderMs = System.currentTimeMillis() - renderStart;
            if (rendered < 0) {
                rendered = rendered(http, chosen, 2);
                renderMs = System.currentTimeMillis() - renderStart;
            }
            if (rendered < 0) {
                System.out.println("slot iterMiss=true productWouldLeaveUnsent=true pass=" + pass
                        + " kept=" + chosen.size() + " renderMs=" + renderMs);
                return 4;
            }
            last = rendered;
            System.out.println("slot pass=" + pass
                    + " kept=" + chosen.size()
                    + " rendered=" + rendered
                    + " renderMs=" + renderMs
                    + " selectMs=" + selectMs
                    + " elapsedMs=" + (System.currentTimeMillis() - started));
            if (rendered <= allowance) {
                System.out.println("slot fit=true passes=" + (pass + 1) + " kept=" + chosen.size()
                        + " rendered=" + rendered);
                return 0;
            }
            for (int i = 0; i < count; i++) {
                if (!seen[i]) {
                    omit[i] = true;
                }
            }
            if (pass == 0) {
                boolean[] keptFlags = new boolean[count];
                for (int i = 0; i < count; i++) {
                    keptFlags[i] = !omit[i];
                }
                spreadProbe(weights, keptFlags, rendered);
            }
        }
        System.out.println("slot sentence=" + LOCKED + " lastRendered=" + last
                + " elapsedMs=" + (System.currentTimeMillis() - started));
        return 3;
    }

    /**
     * The method adds the gap between a rendered total and the included weights.
     * The remainder lands on the earliest included indexes.
     * A rendered count at or under the included sum leaves the weights.
     *
     * @param weights The weights are the character guesses.
     * @param include The include flags name the weights that share the gap.
     * @param rendered The rendered count is the measured prompt.
     */
    private static void spreadProbe(int[] weights, boolean[] include, int rendered) {
        int included = 0;
        int sum = 0;
        for (int i = 0; i < weights.length; i++) {
            if (include != null && (i >= include.length || !include[i])) {
                continue;
            }
            included++;
            sum = sum + weights[i];
        }
        if (included == 0 || rendered <= sum) {
            return;
        }
        int gap = rendered - sum;
        int each = gap / included;
        int rem = gap % included;
        int seen = 0;
        for (int i = 0; i < weights.length; i++) {
            if (include != null && (i >= include.length || !include[i])) {
                continue;
            }
            int add = each;
            if (seen < rem) {
                add = add + 1;
            }
            weights[i] = weights[i] + add;
            seen++;
        }
    }

    /**
     * The method builds a saturated llama transcript.
     * The first line is a system line. Each pair is one user line and one assistant line. The last line is the live user line.
     *
     * @param pairs The pair count is the number of stored turns.
     * @return The return value is the prompt.
     */
    private static ArrayList<Message> saturated(int pairs) {
        ArrayList<Message> messages = new ArrayList<Message>();
        messages.add(new SystemMessage("You are a short assistant. Answer in one sentence and stay inside the window."));
        for (int i = 0; i < pairs; i++) {
            messages.add(new UserMessage("Note " + i + " stays in the window as a small fact about the chat."));
            messages.add(new AssistantMessage("Noted " + i + ". The fact is stored and the reply stays brief for the next turn."));
        }
        messages.add(new UserMessage(PLAIN));
        return messages;
    }

    /**
     * The method counts one rendered llama prompt.
     * The first POST is {@code /apply-template}. The second POST is {@code /tokenize}. A miss on either POST returns {@code -1}.
     *
     * @param http The client is the slot client.
     * @param messages The messages are the prompt to measure.
     * @param timeoutSeconds The timeout is the request timeout in seconds.
     * @return The return value is the token count, or {@code -1} on a miss.
     */
    private int rendered(HttpClient http, List<Message> messages, int timeoutSeconds) {
        String body;
        try {
            body = LlamaCppLlmService.applyTemplateBody(messages);
        } catch (Exception ex) {
            return -1;
        }
        String raw = postLlama(http, "/apply-template", body, timeoutSeconds);
        if (raw == null) {
            return -1;
        }
        String prompt = LlamaCppLlmService.templatePrompt(raw);
        if (prompt == null) {
            return -1;
        }
        String counted = postLlama(http, LlamaCppLlmService.TOKENIZE_PATH,
                LlamaCppLlmService.tokenizeBody(prompt), timeoutSeconds);
        if (counted == null) {
            return -1;
        }
        return LlamaCppLlmService.tokenCount(counted);
    }

    /**
     * The method posts one JSON document to the llama base.
     * A status outside 200 through 299 returns null. An exception returns null. The method does not send an API key.
     *
     * @param http The client is the slot client.
     * @param path The path is the suffix after the base URL.
     * @param json The json is the request body.
     * @param timeoutSeconds The timeout is the request timeout in seconds.
     * @return The return value is the response text, or null on a miss.
     */
    private String postLlama(HttpClient http, String path, String json, int timeoutSeconds) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(LLAMA_BASE + path))
                    .timeout(Duration.ofSeconds(timeoutSeconds))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json == null ? "" : json, StandardCharsets.UTF_8))
                    .build();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                return null;
            }
            return response.body();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * The method reads the llama slot allowance from {@code application.yaml}.
     * The walk stays inside the {@code llama-openapi} block. The file text is not printed.
     * A missing file returns {@code 3584}.
     *
     * @return The return value is the allowance, at least {@code 1}.
     */
    private static int llamaAllowance() {
        int ctx = 4096;
        int reserve = 0;
        InputStream in = LocalPoolSshProbe.class.getClassLoader().getResourceAsStream("application.yaml");
        if (in == null) {
            return 3584;
        }
        try {
            String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            in.close();
            String[] lines = text.split("\n");
            boolean inLlama = false;
            for (int i = 0; i < lines.length; i++) {
                String raw = lines[i];
                String trimmed = raw.trim();
                if (trimmed.startsWith("llama-openapi:")) {
                    inLlama = true;
                    continue;
                }
                if (!inLlama) {
                    continue;
                }
                if (trimmed.length() > 0 && !raw.startsWith(" ") && !raw.startsWith("\t")) {
                    break;
                }
                if (trimmed.startsWith("#") || trimmed.length() == 0) {
                    continue;
                }
                if (trimmed.startsWith("num-ctx:")) {
                    ctx = parsePositive(trimmed.substring("num-ctx:".length()).trim(), ctx);
                } else if (trimmed.startsWith("num-predict:")) {
                    reserve = parsePositive(trimmed.substring("num-predict:".length()).trim(), 0);
                }
            }
        } catch (Exception ex) {
            return 3584;
        }
        int allowance = ctx - reserve;
        if (allowance < 1) {
            return 1;
        }
        return allowance;
    }

    /**
     * The method reads one positive integer.
     * A value that is not a positive integer returns the fallback.
     *
     * @param raw The raw text is the field value.
     * @param fallback The fallback is the value used when the text is not a positive integer.
     * @return The return value is the parsed integer, or the fallback.
     */
    private static int parsePositive(String raw, int fallback) {
        try {
            int parsed = Integer.parseInt(raw.trim());
            if (parsed > 0) {
                return parsed;
            }
        } catch (NumberFormatException ex) {
            return fallback;
        }
        return fallback;
    }

    /**
     * The method prints the reply markers the pane can write.
     *
     * @param text The text is the stripped shell output.
     */
    private static void printSemantics(String text) {
        System.out.println("probe hasLocked=" + text.contains(LOCKED)
                + " hasContext=" + (text.contains("exceed_context") || text.contains("n_prompt_tokens"))
                + " hasBusy=" + text.contains("busy")
                + " hasEngineDown=" + text.contains("engine-down")
                + " hasStack=" + text.contains(".java:"));
    }

    /**
     * The method returns the mode word written on the probe line.
     *
     * @param mode The mode is the second argument.
     * @return The return value is {@code tool}, {@code llama}, or {@code plain}.
     */
    private static String modeLabel(String mode) {
        if ("tool".equals(mode)) {
            return "tool";
        }
        if ("llama".equals(mode)) {
            return "llama";
        }
        return "plain";
    }

    /**
     * The method reads the text after one label.
     * The text ends at the next carriage return or newline. A missing label returns an empty string.
     *
     * @param text The text is the stripped shell output.
     * @param label The label is the field name including the colon.
     * @return The return value is the trimmed field, or an empty string.
     */
    private static String valueAfter(String text, String label) {
        int at = text.indexOf(label);
        if (at < 0) {
            return "";
        }
        int start = at + label.length();
        int end = start;
        while (end < text.length()) {
            char ch = text.charAt(end);
            if (ch == '\r' || ch == '\n') {
                break;
            }
            end++;
        }
        return text.substring(start, end).trim();
    }

    /**
     * The method keeps the end of one shell capture on one line.
     *
     * @param text The text is the stripped shell output.
     * @param cap The cap is the maximum length.
     * @return The return value is the flattened tail.
     */
    private static String tail(String text, int cap) {
        if (text == null || text.length() == 0) {
            return "";
        }
        String flat = text.replace('\r', ' ').replace('\n', ' ');
        if (flat.length() <= cap) {
            return flat;
        }
        return flat.substring(flat.length() - cap);
    }

    /**
     * The method waits until the marker appears or the channel closes after writing.
     * A timeout or a closed channel returns {@code -1}. The clock is the wall clock.
     *
     * @param shell The shell is the open channel.
     * @param captured The buffer is the shell output.
     * @param marker The marker is the text to find.
     * @param budgetMs The budget is the maximum wait.
     * @return The return value is the clock time when the marker appeared, or {@code -1}.
     * @throws InterruptedException The method throws this exception when the sleep is interrupted.
     */
    private static long waitOpen(ChannelShell shell, ByteArrayOutputStream captured, String marker, long budgetMs)
            throws InterruptedException {
        long deadline = System.currentTimeMillis() + budgetMs;
        while (System.currentTimeMillis() < deadline) {
            String text = captured.toString(StandardCharsets.UTF_8);
            if (text.contains(marker)) {
                return System.currentTimeMillis();
            }
            if (!shell.isOpen() && text.length() > 0) {
                return -1L;
            }
            Thread.sleep(200L);
        }
        return -1L;
    }

    /**
     * The method flattens a short refusal onto one line.
     *
     * @param text The text is the stripped shell output.
     * @return The return value is the flattened text, capped at 300 characters.
     */
    private static String oneLine(String text) {
        return tail(text, 300);
    }

    /**
     * The method waits until the captured text contains the marker.
     * A timeout returns {@code -1}. The clock is the wall clock.
     *
     * @param captured The buffer is the shell output.
     * @param marker The marker is the text to find.
     * @param budgetMs The budget is the maximum wait.
     * @return The return value is the clock time when the marker appeared, or {@code -1}.
     * @throws InterruptedException The method throws this exception when the sleep is interrupted.
     */
    private static long waitFor(ByteArrayOutputStream captured, String marker, long budgetMs) throws InterruptedException {
        long deadline = System.currentTimeMillis() + budgetMs;
        while (System.currentTimeMillis() < deadline) {
            String text = captured.toString(StandardCharsets.UTF_8);
            if (text.contains(marker)) {
                return System.currentTimeMillis();
            }
            Thread.sleep(200L);
        }
        return -1L;
    }

    /**
     * The method counts one marker in the captured text.
     *
     * @param captured The buffer is the shell output.
     * @param marker The marker is the text to count.
     * @return The return value is the count.
     */
    private static int countOf(ByteArrayOutputStream captured, String marker) {
        String text = captured.toString(StandardCharsets.UTF_8);
        int count = 0;
        int at = 0;
        while (true) {
            int found = text.indexOf(marker, at);
            if (found < 0) {
                return count;
            }
            count = count + 1;
            at = found + marker.length();
        }
    }

    /**
     * The method waits until the marker has been seen enough times.
     * A timeout returns {@code -1}. The clock is the wall clock.
     *
     * @param captured The buffer is the shell output.
     * @param marker The marker is the text to count.
     * @param need The need is the count that ends the wait.
     * @param budgetMs The budget is the maximum wait.
     * @return The return value is the clock time when the count was reached, or {@code -1}.
     * @throws InterruptedException The method throws this exception when the sleep is interrupted.
     */
    private static long waitForCount(ByteArrayOutputStream captured, String marker, int need, long budgetMs)
            throws InterruptedException {
        long deadline = System.currentTimeMillis() + budgetMs;
        while (System.currentTimeMillis() < deadline) {
            if (countOf(captured, marker) >= need) {
                return System.currentTimeMillis();
            }
            Thread.sleep(200L);
        }
        return -1L;
    }

    /**
     * The method reads the password for one name from {@code users.yaml}.
     * A missing name returns an empty string. The password is not logged.
     *
     * @param name The name is the SSH user.
     * @return The return value is the password, or an empty string.
     * @throws Exception The method throws this exception when the file cannot be read.
     */
    private static String passwordFor(String name) throws Exception {
        InputStream in = LocalPoolSshProbe.class.getClassLoader().getResourceAsStream("users.yaml");
        if (in == null) {
            return "";
        }
        String text = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        in.close();
        String[] lines = text.split("\n");
        String current = "";
        for (String raw : lines) {
            String row = raw.trim();
            if (row.startsWith("- name:")) {
                current = row.substring("- name:".length()).trim();
            } else if (row.startsWith("password:") && name.equals(current)) {
                return row.substring("password:".length()).trim();
            }
        }
        return "";
    }

    /**
     * The method removes ANSI color sequences from the shell text.
     *
     * @param raw The raw text is the captured shell output.
     * @return The return value is the text without those sequences.
     */
    private static String strip(String raw) {
        return raw.replaceAll("\u001B\\[[0-9;]*m", "");
    }
}
