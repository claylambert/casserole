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
 * @author clayl (2026-10-31)
 */
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
}
