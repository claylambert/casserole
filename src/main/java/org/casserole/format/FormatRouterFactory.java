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

import org.casserole.format.fence.FenceDetector;
import org.casserole.format.fence.FenceRenderer;
import org.casserole.format.json.JsonDetector;
import org.casserole.format.json.JsonRenderer;
import org.casserole.format.list.ListDetector;
import org.casserole.format.list.ListRenderer;
import org.casserole.format.markdown.MarkdownDetector;
import org.casserole.format.markdown.MarkdownRenderer;
import org.casserole.format.table.TableDetector;
import org.casserole.format.table.TableRenderer;
import org.springframework.stereotype.Component;

// @aa-sdd PK-0559 docs/specs/java/org/casserole/format/format.spec.md
/**
 * The {@code FormatRouterFactory} type builds a {@link FormatRouter} with the version-one handler set.
 * The order is fixed: JSON, fence, table, list, then markdown.
 * JSON is the object and array text format.
 * A fence is a block wrapped in backtick lines.
 *
 * @author clayl (2026-08-25)
 */
@Component
public class FormatRouterFactory {

    /**
     * The {@code create} method returns a new router.
     * If {@code smartFormat} is {@code false}, the router has no handlers.
     * That empty router keeps the reply text and only normalizes line endings.
     * If {@code smartFormat} is {@code true}, the method registers json, fence, table, list, and markdown, in that order.
     *
     * @param smartFormat {@code false} when the router should stay empty, or {@code true} when the five handlers should be registered.
     * @return The new router.
     */
    public FormatRouter create(boolean smartFormat) {
        FormatRouter router = new FormatRouter();
        if (!smartFormat) {
            return router;
        }
        router.register(new FormatHandler("json", new JsonDetector(), new JsonRenderer()));
        router.register(new FormatHandler("fence", new FenceDetector(), new FenceRenderer()));
        router.register(new FormatHandler("table", new TableDetector(), new TableRenderer()));
        router.register(new FormatHandler("list", new ListDetector(), new ListRenderer()));
        router.register(new FormatHandler("markdown", new MarkdownDetector(), new MarkdownRenderer()));
        return router;
    }
}
/*****************************************************************************************
 * End of Class: FormatRouterFactory.java 082526
 ****************************************************************************************/
