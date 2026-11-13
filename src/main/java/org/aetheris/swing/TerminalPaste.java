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

import org.casserole.app.ui.ssh.ConsolePaste;

// @aa-sdd L40 aetheris-plan-stage-1.001.md
/**
 * The {@code TerminalPaste} class decides what text the terminal will send.
 * The notebook asks for that decision.
 * The client then writes a bracketed paste.
 * This class does not open a window.
 * This class does not append Enter.
 *
 * @author clayl (2026-10-25)
 */
// @aa-sdd PK-122 docs/specs/java/org/aetheris/swing/swing.spec.md
final class TerminalPaste {

    static final int MAX_CHARS = ConsolePaste.MAX_CHARS;

    // @aa-sdd L40 aetheris-plan-stage-1.001.md
    /**
     * The {@code Kind} enum names the four results {@link #decide} can return.
     * {@code EPHEMERAL} means the cell is a burner and nothing is sent.
     * A burner is an ephemeral cell.
     * {@code EMPTY} means there is no text to send.
     * {@code OVER_CAP} means the normalized text is longer than {@link #MAX_CHARS}.
     * {@code READY} means {@code body} holds the text to send.
     */
    enum Kind {
        EPHEMERAL,
        EMPTY,
        OVER_CAP,
        READY
    }

    // @aa-sdd L40 aetheris-plan-stage-1.001.md
    /**
     * The {@code Decision} class is one result from {@link TerminalPaste#decide}.
     * The {@code kind} field names which result it is.
     * The {@code body} field holds the normalized text when the kind is {@link Kind#READY}.
     * The body is empty for every other kind.
     */
    // @aa-sdd GK-1649 aetheris-stage-2.draft.001.md
    // @aa-sdd PK-783 docs/specs/common/stages.spec.md
    // @aa-sdd PK-118 docs/specs/java/org/aetheris/swing/swing.spec.md
    static final class Decision {
        final Kind kind;
        /**
         * The text is the normalized body when the kind is {@link Kind#READY}.
         * The text is empty for every other kind.
         */
        final String body;

        /**
         * The constructor stores the kind and the body.
         *
         * @param kind The kind is one of the four results.
         * @param body The body is the normalized text, or empty when nothing is sent.
         */
        private Decision(Kind kind, String body) {
            this.kind = kind;
            this.body = body;
        }
    }

    /**
     * The constructor stops a caller from creating an instance.
     * The decision is a static method.
     */
    private TerminalPaste() {
    }

    /**
     * The method chooses the text a bracketed paste would send.
     * An ephemeral cell returns {@link Kind#EPHEMERAL} and an empty body.
     * A null reply is treated as empty.
     * A reply whose trimmed length is zero falls through to the prompt.
     * A null prompt is treated as empty.
     * A chosen string whose length is zero returns {@link Kind#EMPTY}.
     * The chosen string is passed through {@link ConsolePaste#normalize}, which turns CR LF and a bare CR into LF.
     * This method does not append Enter.
     * A normalized body longer than {@link ConsolePaste#MAX_CHARS} returns {@link Kind#OVER_CAP} and an empty body.
     * Any shorter body returns {@link Kind#READY}.
     *
     * @param ephemeral The flag is true when the cell is ephemeral.
     * @param assistant The assistant value is the reply text, and it may be null.
     * @param user The user value is the prompt text, and it may be null.
     * @return The return value is the decision for that cell.
     */
    static Decision decide(boolean ephemeral, String assistant, String user) {
        if (ephemeral) {
            return new Decision(Kind.EPHEMERAL, "");
        }
        String text = assistant == null ? "" : assistant;
        if (text.trim().length() == 0) {
            text = user == null ? "" : user;
        }
        if (text.length() == 0) {
            return new Decision(Kind.EMPTY, "");
        }
        String body = ConsolePaste.normalize(text);
        if (body.length() > MAX_CHARS) {
            return new Decision(Kind.OVER_CAP, "");
        }
        return new Decision(Kind.READY, body);
    }
}
