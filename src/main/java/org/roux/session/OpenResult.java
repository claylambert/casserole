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

package org.roux.session;

import java.util.UUID;

/**
 * The OpenResult is the outcome of opening a region.
 * {@code NEW} is {@code 0}, and it means an empty region.
 * {@code RESTORED} is {@code 1}, and it means stored pages were read back.
 * {@code REFUSED} is {@code 2}, and it means the open stopped.
 * A refused result carries a reason string.
 *
 * A null reason is stored as an empty string.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#OpenResult roux-v1.md
 *
 * @author clayl (2026-08-26)
 */
// @aa-sdd PK-819 docs/specs/common/roux.spec.md
public final class OpenResult {

    public static final int NEW = 0;
    public static final int RESTORED = 1;
    public static final int REFUSED = 2;

    private final int kind;
    private final int messageCount;
    private final UUID regionUuid;
    private final String reason;

    /**
     * The constructor stores the kind, the message count, the region id, and the reason.
     * A null reason is stored as an empty string.
     *
     * @param kind The kind is {@code NEW}, {@code RESTORED}, or {@code REFUSED}.
     * @param messageCount The count is the number of indexed messages.
     * @param regionUuid The id is the region identifier, or null.
     * @param reason The reason is the refusal text, or an empty string.
     */
    private OpenResult(int kind, int messageCount, UUID regionUuid, String reason) {
        this.kind = kind;
        this.messageCount = messageCount;
        this.regionUuid = regionUuid;
        this.reason = reason == null ? "" : reason;
    }

    /**
     * The method builds a new-region result.
     * The kind is {@code NEW}.
     * The message count is {@code 0}.
     *
     * @param regionUuid The id is the identifier of the new region.
     * @return The result is a new empty region.
     */
    public static OpenResult neu(UUID regionUuid) {
        return new OpenResult(NEW, 0, regionUuid, "");
    }

    /**
     * The method builds a restored-region result.
     * The kind is {@code RESTORED}.
     *
     * @param messageCount The count is the number of indexed messages.
     * @param regionUuid The id is the identifier of the restored region.
     * @return The result is a restored region.
     */
    public static OpenResult restored(int messageCount, UUID regionUuid) {
        return new OpenResult(RESTORED, messageCount, regionUuid, "");
    }

    /**
     * The method builds a refused result.
     * The kind is {@code REFUSED}.
     * The region id is null.
     *
     * @param reason The reason is the text that says why the open stopped.
     * @return The result is a refusal.
     */
    public static OpenResult refused(String reason) {
        return new OpenResult(REFUSED, 0, null, reason);
    }

    /**
     * The method returns the stored kind.
     *
     * @return The kind is {@link #NEW}, {@link #RESTORED}, or {@link #REFUSED}.
     */
    public int getKind() {
        return kind;
    }

    /**
     * The method returns the stored message count.
     *
     * @return The count is the number of indexed messages.
     */
    public int getMessageCount() {
        return messageCount;
    }

    /**
     * The method returns the stored region identifier.
     *
     * @return The id is the region identifier, or null.
     */
    public UUID getRegionUuid() {
        return regionUuid;
    }

    /**
     * The method returns the stored reason.
     *
     * @return The reason is the refusal text, or an empty string.
     */
    public String getReason() {
        return reason;
    }

    /**
     * The method reports whether the open succeeded.
     * A new result succeeds.
     * A restored result succeeds.
     *
     * @return The flag is true when the kind is {@link #NEW} or {@link #RESTORED}.
     */
    public boolean isOk() {
        return kind == NEW || kind == RESTORED;
    }
}
/*****************************************************************************************
 * End of Class: OpenResult.java 082626
 ****************************************************************************************/
