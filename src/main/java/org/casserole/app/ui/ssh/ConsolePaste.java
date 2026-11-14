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
 * The {@code ConsolePaste} type holds the newline rule for pasted text.
 * The type also holds {@link #MAX_CHARS}, the paste cap shared by the paste editor, Send file, and To terminal.
 * The {@link #normalize} method turns a carriage return into a line feed.
 * The line feed is the newline kept by a bracketed paste, one blob whose newlines stay inside the blob.
 * The type does not append an Enter character.
 * // @aa-sdd L40 aa-sdd-source-prose.md#ConsolePaste
 *
 * @author clayl (2026-10-25)
 */
// @aa-sdd PK-258 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
public final class ConsolePaste {

    /**
     * The {@code MAX_CHARS} constant is the shared paste cap.
     * The value is {@code 65536} characters.
     * The number matches the default of {@code casserole.chat.max-paste-chars}.
     */
    public static final int MAX_CHARS = 65536;

    /**
     * The constructor is private so a caller cannot create an instance.
     * The caller uses {@link #normalize} and {@link #MAX_CHARS}.
     */
    private ConsolePaste() {
    }

    /**
     * The normalize method turns carriage returns into line feeds.
     * The method turns a carriage return followed by a line feed into one line feed.
     * The method turns a bare carriage return into one line feed.
     * The method returns an empty string when the text is null.
     * The method does not append an Enter character.
     * The method does not cut the text at {@link #MAX_CHARS}.
     *
     * @param text The raw text. The value may be null.
     * @return The return value is the text with a line feed for each newline.
     */
    public static String normalize(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("\r\n", "\n").replace('\r', '\n');
    }
}
