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

package org.casserole.config;

// @aa-sdd L40 app-design.md
/**
 * The {@code UserEntry} type is one password-authentication row from {@code users.yaml}.
 * {@code users.yaml} is the file that lists SSH login names and passwords.
 * SSH is the secure-shell protocol a chat client uses to connect.
 * The entry holds the sanitized user name and the password.
 *
 * @author clayl (2026-08-26)
 */
// @aa-sdd PK-340 docs/specs/java/org/casserole/config/config.spec.md
public final class UserEntry {

    private final String name;
    private final String password;

    /**
     * The constructor stores the sanitized user name and the password.
     * The password is a non-empty secret.
     *
     * @param name The sanitized user name.
     * @param password The non-empty password.
     */
    public UserEntry(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * The {@code getName} method returns the user name.
     *
     * @return The sanitized user name stored by the constructor.
     */
    public String getName() {
        return name;
    }

    /**
     * The {@code getPassword} method returns the password.
     *
     * @return The password stored by the constructor.
     */
    public String getPassword() {
        return password;
    }
}
/*****************************************************************************************
 * End of Class: UserEntry.java 082626
 ****************************************************************************************/
