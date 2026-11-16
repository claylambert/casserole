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

package org.casserole.format.table;

import org.casserole.format.FormatContext;
import org.casserole.format.FormatRenderer;
import org.casserole.format.FormatResult;

/**
 * The {@code TableRenderer} type turns a GitHub-flavored pipe table into a text table.
 * A pipe table is a header row, a separator row, and data rows divided by {@code |}.
 * The drawing style is ASCII or box, taken from the format context.
 * The drawer paints its own SGR color sequences.
 * SGR is the select-graphic-rendition sequence that sets terminal color.
 * The result turns body color off so the shell does not wrap another color around the table.
 *
 * @author clayl (2026-08-25)
 */
public class TableRenderer implements FormatRenderer {

    private final TextTableDrawer drawer;

    /**
     * The constructor stores a new {@link TextTableDrawer}.
     */
    public TableRenderer() {
        this.drawer = new TextTableDrawer();
    }

    /**
     * The {@code render} method draws a parsed table.
     * If {@link TableDetector#parse} returns {@code null}, the method returns the original text with body color on.
     * Otherwise {@link TextTableDrawer#draw} paints the model and the result has body color off.
     *
     * @param raw The original assistant text.
     * @param ctx The render settings passed to the drawer.
     * @return The drawn table, or the original text when the reply is not a table.
     */
    @Override
    public FormatResult render(String raw, FormatContext ctx) {
        TableModel model = TableDetector.parse(raw);
        if (model == null) {
            return new FormatResult(raw, true);
        }
        String drawn = drawer.draw(model, ctx);
        return new FormatResult(drawn, false);
    }
}
/*****************************************************************************************
 * End of Class: TableRenderer.java 082526
 ****************************************************************************************/
