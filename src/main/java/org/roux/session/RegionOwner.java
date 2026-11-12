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

/**
 * The RegionOwner type checks a user name before it is used as a folder name.
 * A valid name is letters, digits, a dot, an underscore, or a hyphen.
 * The name {@code .} is refused.
 * The name {@code ..} is refused.
 * A colon is refused.
 * An empty name is refused.
 *
 * The type refuses construction.
 *
 * @aa-sdd PK-3481 PK-3483 docs/specs/java/org/roux/roux.spec.md
 *
 * @author clayl (2026-08-26)
 */
public final class RegionOwner {

    /**
     * The constructor blocks a caller from creating an instance.
     */
    private RegionOwner() {
    }

    /**
     * The method reports whether a user name is a safe folder name.
     * A null name returns false.
     * An empty name returns false.
     *
     * @param raw The raw value is the user name to check.
     * @return The flag is true when every character is allowed.
     */
    public static boolean isValid(String raw) {
        if (raw == null || raw.length() == 0) {
            return false;
        }
        if (raw.equals(".") || raw.equals("..")) {
            return false;
        }
        for (int i = 0; i < raw.length(); i++) {
            char c = raw.charAt(i);
            boolean ok = (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z') || (c >= '0' && c <= '9')
                    || c == '.' || c == '_' || c == '-';
            if (!ok) {
                return false;
            }
        }
        return true;
    }

    /**
     * The method returns the same user name when it is a safe folder name.
     * An invalid name throws {@link IllegalArgumentException}.
     * The exception message is {@code invalid username}.
     *
     * @param raw The raw value is the user name to check.
     * @return The name is the same string that was passed.
     * @throws IllegalArgumentException The name is not a safe folder name.
     */
    public static String sanitize(String raw) {
        if (!isValid(raw)) {
            throw new IllegalArgumentException("invalid username");
        }
        return raw;
    }
}
/*****************************************************************************************
 * End of Class: RegionOwner.java 082626
 ****************************************************************************************/
