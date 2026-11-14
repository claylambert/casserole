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

package org.roux.model;

/**
 * The OperationResult is the object returned by a call that changes stored bytes.
 * The object stores whether the call succeeded.
 * The object stores a status code from {@link OperationStatus}.
 * The object stores a message.
 *
 * A caller reads those three values after the call.
 * A null message is stored as an empty string.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#OperationResult roux-design.md
 *
 * @author clayl (2026-08-25)
 */
// @aa-sdd PK-491 docs/specs/java/org/roux/model/model.spec.md
public class OperationResult {

    private final boolean success;
    private final int status;
    private final String message;

    /**
     * The constructor stores the success flag, the status code, and the message.
     * A null message is stored as an empty string.
     *
     * @param success The flag is true when the call succeeded.
     * @param status The status is a code from {@link OperationStatus}.
     * @param message The message is the detail text, or an empty string.
     */
    public OperationResult(boolean success, int status, String message) {
        this.success = success;
        this.status = status;
        this.message = message == null ? "" : message;
    }

    /**
     * The method builds a successful result.
     * The status code is {@link OperationStatus#SUCCESS}.
     *
     * @param message The message is the detail text stored on the result.
     * @return The result is a success.
     */
    public static OperationResult ok(String message) {
        return new OperationResult(true, OperationStatus.SUCCESS, message);
    }

    /**
     * The method builds a failed result.
     * The success flag is false.
     *
     * @param status The status is a code from {@link OperationStatus}.
     * @param message The message is the detail text stored on the result.
     * @return The result is a failure.
     */
    public static OperationResult fail(int status, String message) {
        return new OperationResult(false, status, message);
    }

    /**
     * The method returns the stored success flag.
     *
     * @return The flag is true when the call succeeded.
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * The method returns the stored status code.
     *
     * @return The code is a value from {@link OperationStatus}.
     */
    public int getStatus() {
        return status;
    }

    /**
     * The method returns the stored message.
     *
     * @return The message is the detail text, or an empty string.
     */
    public String getMessage() {
        return message;
    }
}
/*****************************************************************************************
 * End of Class: OperationResult.java 082526
 ****************************************************************************************/
