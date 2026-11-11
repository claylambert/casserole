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

import java.util.ArrayList;
import java.util.List;

/**
 * The {@code FormatRouter} type paints an assistant reply with the first matching handler.
 * If no handler matches, the reply is flowed as a paragraph and then given VT100 line endings.
 * VT100 is the terminal control set the chat shell paints with.
 * {@link LinkAnnotator} then marks links in the finished text.
 *
 * @author clayl (2026-08-25)
 */
public class FormatRouter {

    private final List<FormatHandler> handlers;

    /**
     * The constructor creates a router with no handlers.
     * A router with no handlers keeps the reply text, flows the paragraph, and normalizes line endings.
     */
    public FormatRouter() {
        this.handlers = new ArrayList<FormatHandler>();
    }

    /**
     * The {@code register} method appends one handler.
     * {@link #format} tries handlers in this registration order.
     *
     * @param handler The detector and renderer pair to append.
     */
    public void register(FormatHandler handler) {
        handlers.add(handler);
    }

    /**
     * The {@code getHandlers} method returns the registered handlers.
     * The list is the live list, so a later change to it changes the router.
     *
     * @return The handlers in registration order.
     */
    public List<FormatHandler> getHandlers() {
        return handlers;
    }

    /**
     * The {@code format} method paints one assistant reply.
     * If {@code raw} is {@code null} or empty, the method returns an empty {@link FormatResult} with body color on.
     * A {@code null} context is replaced with a new {@link FormatContext}.
     * The first handler whose detector matches paints the reply.
     * The painted text is passed through {@link LineDiscipline#toCrlf} and {@link LineDiscipline#ensureTrailingCrlf}.
     * {@link LinkAnnotator#annotate} then marks links, and the handler's body-color flag is kept.
     * If no handler matches, {@link ParagraphFlow#flow} flows the paragraph before the same line-ending and link steps.
     * That unmatched result has body color on.
     *
     * @param raw The original assistant text.
     * @param ctx The render settings, or {@code null} to use a fresh {@link FormatContext}.
     * @return The formatted result.
     */
    public FormatResult format(String raw, FormatContext ctx) {
        if (raw == null || raw.length() == 0) {
            return new FormatResult("", true);
        }
        FormatContext context = ctx == null ? new FormatContext() : ctx;
        for (int i = 0; i < handlers.size(); i++) {
            FormatHandler handler = handlers.get(i);
            if (handler.getDetector().matches(raw)) {
                FormatResult rendered = handler.getRenderer().render(raw, context);
                String crlf = LineDiscipline.ensureTrailingCrlf(LineDiscipline.toCrlf(rendered.getText()));
                return new FormatResult(LinkAnnotator.annotate(crlf), rendered.isApplyBodyColor());
            }
        }
        // @aa-sdd PK-0128 docs/specs/java/org/casserole/format/format.spec.md
        String flowed = ParagraphFlow.flow(raw);
        String identity = LineDiscipline.ensureTrailingCrlf(LineDiscipline.toCrlf(flowed));
        return new FormatResult(LinkAnnotator.annotate(identity), true);
    }
}
/*****************************************************************************************
 * End of Class: FormatRouter.java 082526
 ****************************************************************************************/
