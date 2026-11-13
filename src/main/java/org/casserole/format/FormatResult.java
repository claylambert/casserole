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

package org.casserole.format;

// @aa-sdd L40 aetheris-stage-2-finessing.001.md
/**
 * The {@code FormatResult} type is the output of a renderer.
 * The type holds the painted text and the {@code applyBodyColor} flag.
 * That flag tells the shell whether to wrap the body color around the whole reply.
 * The body color is yellow, or another color the shell has chosen for assistant text.
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-350 docs/specs/java/org/casserole/format/format.spec.md
public class FormatResult {

    private final String text;
    private final boolean applyBodyColor;

    /**
     * The constructor stores the painted text and the body-color flag.
     * A {@code null} text is stored as the empty string.
     * The text is VT100 text, and it is typically already written with carriage-return line feeds.
     * VT100 is the terminal control set the chat shell paints with.
     *
     * @param text The VT100 text, typically already using carriage-return line feeds.
     * @param applyBodyColor {@code true} when the shell should wrap the body color around the whole reply.
     */
    public FormatResult(String text, boolean applyBodyColor) {
        this.text = text == null ? "" : text;
        this.applyBodyColor = applyBodyColor;
    }

    /**
     * The {@code getText} method returns the painted text.
     *
     * @return The rendered text stored by the constructor.
     */
    public String getText() {
        return text;
    }

    /**
     * The {@code isApplyBodyColor} method reports whether the shell should apply the default assistant color.
     *
     * @return {@code true} when the chat shell should wrap the body color around the reply.
     */
    public boolean isApplyBodyColor() {
        return applyBodyColor;
    }
}
/*****************************************************************************************
 * End of Class: FormatResult.java 082526
 ****************************************************************************************/
