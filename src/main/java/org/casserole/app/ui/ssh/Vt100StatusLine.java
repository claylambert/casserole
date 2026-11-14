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

package org.casserole.app.ui.ssh;

/**
 * The {@code Vt100StatusLine} type builds the thin status line for the VT100 console.
 * The name VT100 means the console mode whose wire word on this line is {@code VT100} or {@code PTY}.
 * The link word leads the line.
 * The link word is the connection text from {@link #west}.
 * The line does not print the word {@code AMWP}, the name of the Aetheris frame protocol.
 * The line does not print the word {@code raw}.
 * The two words stay on the Aetheris strip, the status strip of the canvas tab.
 * // @aa-sdd L40 aa-sdd-source-prose.md#Vt100StatusLine
 *
 * @author clayl (2026-10-31)
 */
// @aa-sdd PK-1296 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-775 docs/specs/common/stages.spec.md
// @aa-sdd PK-668 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-669 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-1360 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
public final class Vt100StatusLine {

    /**
     * The {@code MOOD_ACTIVE} constant is the text added when the SSH mood gate is on.
     * The text is {@code mood active}.
     * The mood string itself stays off this strip.
     */
    public static final String MOOD_ACTIVE = "mood active";

    /**
     * The constructor is private so a caller cannot create an instance.
     * The caller uses the static methods on this type.
     */
    private Vt100StatusLine() {
    }

    /**
     * The modeWord method turns the stored connection mode into the wire word.
     * The stored mode is the token under {@code ui.connection.mode}.
     * The method returns {@code PTY} when the trimmed token equals {@code pty}, ignoring letter case.
     * The method returns {@code VT100} for a null token, a blank token, and every other token.
     *
     * @param token The stored connection mode. The value may be null.
     * @return The return value is {@code PTY} or {@code VT100}.
     */
    public static String modeWord(String token) {
        if (token != null && token.trim().equalsIgnoreCase("pty")) {
            return "PTY";
        }
        return "VT100";
    }

    /**
     * The airWord method returns the air-gap word for the status line.
     * The air-gap word is the stored text that says whether the air gap is set.
     * The method returns {@code unset} when the word is null.
     * The method returns {@code unset} when the trimmed word is empty.
     * The method returns the trimmed word for every other value.
     *
     * @param air The stored air-gap word. The value may be null.
     * @return The return value is the trimmed word, or {@code unset} when the word is blank.
     */
    public static String airWord(String air) {
        if (air == null) {
            return "unset";
        }
        String trimmed = air.trim();
        if (trimmed.length() == 0) {
            return "unset";
        }
        return trimmed;
    }

    /**
     * The engineWord method returns the execution target for the status line.
     * The execution target is the stored engine name.
     * The method returns {@code ollama} when the target is null.
     * The method returns {@code ollama} when the trimmed target is empty.
     * The method returns the trimmed target for every other value.
     *
     * @param engine The stored execution target. The value may be null.
     * @return The return value is the trimmed target, or {@code ollama} when the target is blank.
     */
    public static String engineWord(String engine) {
        if (engine == null) {
            return "ollama";
        }
        String trimmed = engine.trim();
        if (trimmed.length() == 0) {
            return "ollama";
        }
        return trimmed;
    }

    /**
     * The facts method joins the vault word, the mode word, the air-gap word, and the engine word.
     * The vault word is {@code disk}.
     * The text begins with {@code vault: disk}.
     * The next two spaces follow that phrase.
     * The mode word follows those spaces.
     * The next two spaces follow the mode word.
     * The label {@code air-gap:} follows those spaces.
     * The air word follows that label.
     * The next two spaces follow the air word.
     * The label {@code engine:} follows those spaces.
     * The engine word follows that label.
     * The mode word comes from {@link #modeWord}.
     * The air word comes from {@link #airWord}.
     * The engine word comes from {@link #engineWord}.
     *
     * @param modeToken The stored connection mode under {@code ui.connection.mode}.
     * @param air The air-gap word.
     * @param engine The execution target.
     * @return The return value is that fact string.
     */
    public static String facts(String modeToken, String air, String engine) {
        return "vault: disk  " + modeWord(modeToken)
                + "  air-gap: " + airWord(air)
                + "  engine: " + engineWord(engine);
    }

    /**
     * The west method builds the link word at the left of the status line.
     * The method treats a null status as an empty string.
     * The method trims the status before it tests the word.
     * The method returns {@code Connecting} when the trimmed status is {@code Connecting} or begins with that word.
     * The method returns {@code Connected}, a space, the host, a colon, and the port when the trimmed status is exactly {@code Connected}.
     * The method treats a null host as an empty string on that branch.
     * The method treats a null port as an empty string on that branch.
     * The method returns the trimmed status unchanged when it begins with {@code Connected} and is not exactly that word.
     * The host and the port are ignored on that branch.
     * The method returns {@code Disconnected} when the status begins with {@code Disconnected} and no reason remains.
     * The method strips one leading colon, hyphen, or em dash from the reason.
     * The method trims the reason and repeats that strip while such a mark remains.
     * The method returns {@code Disconnected}, a space, an em dash, a space, and the reason when a reason remains.
     * The method returns the trimmed status for every other word.
     *
     * @param status The link word. The value may be null.
     * @param host The connected host. The value may be null.
     * @param port The connected port. The value may be null.
     * @return The return value is the link word for the left of the line.
     */
    public static String west(String status, String host, String port) {
        String text = status == null ? "" : status.trim();
        if (text.equals("Connecting") || text.startsWith("Connecting")) {
            return "Connecting";
        }
        if (text.equals("Connected")) {
            return "Connected " + hostPort(host, port);
        }
        if (text.startsWith("Connected")) {
            return text;
        }
        if (text.startsWith("Disconnected")) {
            String rest = text.substring("Disconnected".length()).trim();
            while (rest.length() > 0) {
                char mark = rest.charAt(0);
                if (mark == ':' || mark == '-' || mark == '\u2014') {
                    rest = rest.substring(1).trim();
                } else {
                    break;
                }
            }
            if (rest.length() == 0) {
                return "Disconnected";
            }
            return "Disconnected \u2014 " + rest;
        }
        return text;
    }

    /**
     * The join method builds the strip from the link word, the mood gate, and the facts.
     * The method treats a null link as an empty string.
     * The method puts {@link #MOOD_ACTIVE} alone when the mood gate is on and the link is empty.
     * The method appends two spaces and {@link #MOOD_ACTIVE} when the mood gate is on and the link is not empty.
     * The method returns that head alone when the facts are null or empty.
     * The method returns the facts alone when the head is empty.
     * The method returns the head, two spaces, and the facts when both parts have text.
     *
     * @param link The link word from {@link #west}. The value may be null.
     * @param moodGate The SSH mood gate. The gate is on when the flag is true.
     * @param facts The vault, mode, air-gap, and engine text from {@link #facts}.
     * @return The return value is the joined strip.
     */
    public static String join(String link, boolean moodGate, String facts) {
        String head = link == null ? "" : link;
        if (moodGate) {
            if (head.length() == 0) {
                head = MOOD_ACTIVE;
            } else {
                head = head + "  " + MOOD_ACTIVE;
            }
        }
        if (facts == null || facts.length() == 0) {
            return head;
        }
        if (head.length() == 0) {
            return facts;
        }
        return head + "  " + facts;
    }

    /**
     * The hostPort method joins the host and the port with a colon.
     * The method turns a null host into an empty string.
     * The method turns a null port into an empty string.
     *
     * @param host The connected host. The value may be null.
     * @param port The connected port. The value may be null.
     * @return The return value is the host, a colon, and the port.
     */
    private static String hostPort(String host, String port) {
        String heldHost = host == null ? "" : host;
        String heldPort = port == null ? "" : port;
        return heldHost + ":" + heldPort;
    }

    /**
     * The engineFromConsole method builds the SSH strip engine from one pane's selected model.
     * Pool returns {@code pool}. Grok returns {@code grok/} plus the id, or {@code grok} when the id is blank.
     * Any other family returns {@code ollama/} plus the Ollama id, or {@code ollama} when that id is blank.
     * An id that already begins with its family prefix is returned as stored.
     * A canvas cell is not an input.
     *
     * @param provider The family word. The value may be null. A blank word is Ollama.
     * @param ollamaId The Ollama id. The value may be null.
     * @param grokId The Grok id. The value may be null.
     * @return The return value is the engine word for that pane.
     */
    public static String engineFromConsole(String provider, String ollamaId, String grokId) {
        String family = provider == null ? "" : provider.trim();
        if ("pool".equals(family)) {
            return "pool";
        }
        if ("grok".equals(family)) {
            String id = grokId == null ? "" : grokId.trim();
            if (id.length() == 0) {
                return "grok";
            }
            if (id.startsWith("grok/")) {
                return id;
            }
            return "grok/" + id;
        }
        String id = ollamaId == null ? "" : ollamaId.trim();
        if (id.length() == 0) {
            return "ollama";
        }
        if (id.startsWith("ollama/")) {
            return id;
        }
        return "ollama/" + id;
    }

    /**
     * The engineFromShellResult method reads a {@code shell_model_result:} body.
     * A null body, or a body that does not start with that prefix, returns null.
     * The provider line selects the family. A missing provider line is Ollama.
     * The Ollama id is {@code ollama_model:}, or {@code model:} when that line is empty.
     * The Grok id is {@code grok_model:}. The word is {@link #engineFromConsole}.
     *
     * @param text The frame text. The value may be null.
     * @return The return value is the pane engine word, or null when the text is not a shell-model result.
     */
    public static String engineFromShellResult(String text) {
        if (text == null || !text.startsWith("shell_model_result:")) {
            return null;
        }
        String provider = consoleLine(text, "provider:");
        String ollama = consoleLine(text, "ollama_model:");
        if (ollama.length() == 0) {
            ollama = consoleLine(text, "model:");
        }
        String grok = consoleLine(text, "grok_model:");
        return engineFromConsole(provider, ollama, grok);
    }

    /**
     * The consoleLine method reads one prefixed line from a shell-model body.
     * A null body or a null key returns empty. A trailing carriage return is removed.
     * The value is the text after the key, trimmed. A missing line returns empty.
     *
     * @param text The body. The value may be null.
     * @param key The line prefix, including the colon. The value may be null.
     * @return The return value is the value, or an empty string.
     */
    private static String consoleLine(String text, String key) {
        if (text == null || key == null) {
            return "";
        }
        int start = 0;
        while (start <= text.length()) {
            int nl = text.indexOf('\n', start);
            String line = nl < 0 ? text.substring(start) : text.substring(start, nl);
            if (line.endsWith("\r")) {
                line = line.substring(0, line.length() - 1);
            }
            if (line.startsWith(key)) {
                return line.substring(key.length()).trim();
            }
            if (nl < 0) {
                break;
            }
            start = nl + 1;
        }
        return "";
    }
}
