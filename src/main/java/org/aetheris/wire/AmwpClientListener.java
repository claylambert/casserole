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

package org.aetheris.wire;

// @aa-sdd PK-1056 docs/specs/common/formal-plan.spec.md FINESSE:f-36
/**
 * The {@code AmwpClientListener} interface is the set of callbacks the shell client receives.
 * A callback runs off the Swing event thread.
 * The event thread is the thread that paints the window.
 * Scope A is channel A, the value {@link AmwpConstants#CHANNEL_A}.
 * // @aa-sdd G-07 aetheris-stages-1-3.finalize.md
 *
 * @author clayl (2026-09-02)
 */
// @aa-sdd PK-760 docs/specs/common/stages.spec.md
// @aa-sdd PK-1362 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-671 docs/specs/common/formal-plan.spec.md
public interface AmwpClientListener {

    /**
     * The method receives one Scope A frame or one stream frame.
     * The call runs off the Swing event thread.
     *
     * @param frame The frame is the decoded AMWP frame.
     */
    void onAmwpFrame(AmwpFrame frame);

    /**
     * The method reports that the framed channel dropped back to raw bytes or closed.
     * The call runs off the Swing event thread.
     *
     * @param reason The reason is the text that explains the close.
     */
    void onAmwpClosed(String reason);

    /**
     * The method reports that the console asked for the model settings.
     * The call runs off the Swing event thread.
     *
     * @param model The model is the Ollama id from the shell, and it is empty when the shell has none.
     * @param think The think value is empty when thinking is off, {@code true} when thinking is on, and otherwise the level.
     */
    void onConsoleModel(String model, String think);
}
/*****************************************************************************************
 * End of Class: AmwpClientListener.java 090226
 ****************************************************************************************/
