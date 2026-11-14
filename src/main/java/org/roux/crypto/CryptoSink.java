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

package org.roux.crypto;

import org.roux.RouxProperties;
import org.roux.model.OperationResult;
import org.roux.model.OperationStatus;
import org.springframework.stereotype.Component;

// @aa-sdd PK-970 docs/specs/java/org/casserole/ssh/shell/shell.spec.md
/**
 * The CryptoSink answers whether encryption is turned on.
 * The sink reads the crypto setting from {@link RouxProperties}.
 * An enabled setting is refused.
 * A disabled setting lets the caller continue.
 *
 * // @aa-sdd L40 aa-sdd-source-prose.md#CryptoSink roux-design.md
 *
 * @author clayl (2026-08-25)
 */
@Component
// @aa-sdd PK-688 docs/specs/common/pre-plan.spec.md
// @aa-sdd PK-638 docs/specs/common/formal-plan.spec.md
public class CryptoSink {

    private final RouxProperties properties;

    /**
     * The constructor stores the settings.
     *
     * @param properties The properties are the settings this sink reads.
     */
    public CryptoSink(RouxProperties properties) {
        this.properties = properties;
    }

    /**
     * The method reports whether encryption is enabled.
     * A missing crypto block is treated as disabled.
     *
     * @return The flag is true when the crypto setting is enabled.
     */
    public boolean isEnabled() {
        return properties.getCrypto() != null && properties.getCrypto().isEnabled();
    }

    /**
     * The method refuses an enabled cipher.
     * The failure text is {@code roux.crypto.enabled is not implemented in v1}.
     * The failure status is {@link OperationStatus#UNSUPPORTED}.
     * A disabled setting returns success with an empty message.
     *
     * // @aa-sdd L40 aa-sdd-source-prose.md#CryptoSink roux-design.md
     *
     * @return The result is a failure when encryption is enabled.
     */
    public OperationResult assertDisabled() {
        if (isEnabled()) {
            return OperationResult.fail(OperationStatus.UNSUPPORTED, "roux.crypto.enabled is not implemented in v1");
        }
        return OperationResult.ok("");
    }
}
/*****************************************************************************************
 * End of Class: CryptoSink.java 082526
 ****************************************************************************************/
