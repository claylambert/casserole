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

package org.casserole.chat;

// @aa-sdd GK-0952 FEAT-006.stop-spinner-timer.md
/**
 * The {@code ThinkingClock} type formats the elapsed time shown beside a thinking spinner.
 * A thinking spinner is the animation that runs while a request is still in progress.
 * The count starts when the request starts.
 * The type has no instances.
 *
 * @author clayl (2026-10-20)
 */
// @aa-sdd GK-1346 aetheris-plan-stage-2.001.md
// @aa-sdd PK-288 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-289 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-290 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-291 docs/specs/java/org/casserole/chat/chat.spec.md
// @aa-sdd PK-676 docs/specs/common/formal-plan.spec.md
// @aa-sdd PK-295 docs/specs/java/org/casserole/chat/chat.spec.md
public final class ThinkingClock {

    /**
     * The constructor is private so a caller uses {@link #format(long)} instead of an instance.
     */
    private ThinkingClock() {
    }

    /**
     * The {@code format} method turns a request start time into a clock string.
     * The {@code startedAtMs} value is the millisecond clock from {@code System.currentTimeMillis} when the request started.
     * If {@code startedAtMs} is {@code 0}, the clock has not started.
     * The elapsed time is then zero.
     * If the current clock is earlier than the start, the elapsed time is zero.
     * The result is {@code m:ss} while the elapsed time is under sixty minutes.
     * The result is {@code h:mm:ss} once the elapsed time reaches sixty minutes.
     * {@link #two(long)} writes each minute and second field with a leading zero when that field is below ten.
     *
     * @param startedAtMs The millisecond clock when the request started, or {@code 0} when that clock has not started.
     * @return The elapsed time as {@code m:ss}, or as {@code h:mm:ss} after sixty minutes.
     */
    public static String format(long startedAtMs) {
        long elapsed = 0L;
        if (startedAtMs > 0L) {
            elapsed = System.currentTimeMillis() - startedAtMs;
            if (elapsed < 0L) {
                elapsed = 0L;
            }
        }
        long totalSeconds = elapsed / 1000L;
        long hours = totalSeconds / 3600L;
        long minutes = (totalSeconds % 3600L) / 60L;
        long seconds = totalSeconds % 60L;
        if (hours > 0L) {
            return hours + ":" + two(minutes) + ":" + two(seconds);
        }
        return minutes + ":" + two(seconds);
    }

    /**
     * The {@code two} method writes a minute or second count as text.
     * If the count is below ten, the text has a leading zero.
     * If the count is ten or more, the text is the decimal form of that count.
     *
     * @param value The minute or second count to write.
     * @return The count as two characters when it is below ten, or the full decimal text otherwise.
     */
    private static String two(long value) {
        if (value < 10L) {
            return "0" + value;
        }
        return Long.toString(value);
    }
}
