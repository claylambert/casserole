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

/**
 * The {@code FormatRenderer} type turns a matched reply into VT100 text.
 * VT100 is the terminal control set the chat shell paints with.
 *
 * @author clayl (2026-08-25)
 */
public interface FormatRenderer {

    /**
     * The {@code render} method paints one matched reply.
     * The {@code ctx} value supplies the column width and the optional SGR sequences.
     * SGR is the select-graphic-rendition sequence that sets terminal color.
     *
     * @param raw The original assistant text.
     * @param ctx The column width and the optional SGR sequences.
     * @return The rendered {@link FormatResult}.
     */
    FormatResult render(String raw, FormatContext ctx);
}
/*****************************************************************************************
 * End of Class: FormatRenderer.java 082526
 ****************************************************************************************/
