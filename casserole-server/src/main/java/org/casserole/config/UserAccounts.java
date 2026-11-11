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

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.roux.session.RegionOwner;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;

// @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
// @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
// @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
// @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
// @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
// @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
// @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
// @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
// @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
// @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
/**
 * The {@code UserAccounts} type loads the SSH password accounts for the server.
 * SSH is the secure-shell protocol a client uses to connect.
 * The load reads {@code ./users.yaml}, then {@code user.home/.casserole/users.yaml}, then the classpath resource {@code users.yaml}.
 * The last step uses the {@code casserole.ssh} username and password from {@link CasseroleProperties}.
 * The successful load stores a non-empty list of {@link UserEntry} rows and a source label.
 * The type refuses an empty file, a root that is not a map, a missing {@code users} key, a {@code users} value that is not a list, an empty list, a duplicate name, a file with no valid rows, an unreadable file, and an unusable fallback pair.
 * The load skips a non-map row, a blank name, a name {@code RegionOwner.isValid} rejects, and an empty password, and it prints each skip to standard error.
 *
 * @author clayl (2026-08-26)
 */
@Component
public class UserAccounts {

    /**
     * The loaded account rows.
     * The list is the list chosen at construction.
     */
    private final List<UserEntry> entries;

    /**
     * The label of the place those rows came from.
     */
    private final String source;

    /**
     * The constructor loads accounts through {@link #loadCascade}.
     * The stored list and the source label come from that load.
     *
     * @param properties The configuration that supplies the fallback username and password.
     * @throws IllegalStateException The chosen file or the fallback pair cannot be used.
     */
    public UserAccounts(CasseroleProperties properties) {
        Loaded loaded = loadCascade(properties);
        this.entries = loaded.entries;
        this.source = loaded.source;
    }

    /**
     * The {@code getEntries} method returns the loaded account rows.
     * The method returns the same list stored at construction.
     * A successful construction leaves that list non-empty.
     *
     * @return The loaded {@link UserEntry} rows.
     */
    public List<UserEntry> getEntries() {
        return entries;
    }

    /**
     * The {@code getSource} method returns the label of the place the rows came from.
     * The file load uses the absolute path.
     * The classpath load uses {@code classpath:users.yaml}.
     * The fallback uses {@code casserole.ssh}.
     *
     * @return The origin label.
     */
    public String getSource() {
        return source;
    }

    /**
     * The {@code authenticate} method reports whether one row matches the name and the password.
     * The method returns false when the username is null.
     * The method returns false when the password is null.
     * The match is an exact {@code equals} on the name and on the password.
     * The method returns false when no row matches.
     *
     * @param username The name to match.
     * @param password The password to match.
     * @return Whether a stored row matches both values.
     */
    public boolean authenticate(String username, String password) {
        if (username == null || password == null) {
            return false;
        }
        for (int i = 0; i < entries.size(); i++) {
            UserEntry row = entries.get(i);
            if (username.equals(row.getName()) && password.equals(row.getPassword())) {
                return true;
            }
        }
        return false;
    }

    /**
     * The {@code firstName} method returns the first account name.
     * The banner uses that name.
     * The method returns an empty string when the list is empty.
     *
     * @return The first {@link UserEntry} name, or an empty string.
     */
    public String firstName() {
        if (entries.isEmpty()) {
            return "";
        }
        return entries.get(0).getName();
    }

    /**
     * The {@code loadCascade} method loads the first account file that exists.
     * The method reads {@code ./users.yaml} when that path is a regular file.
     * The method otherwise reads {@code user.home/.casserole/users.yaml} when that path is a regular file.
     * The method otherwise reads the classpath resource {@code users.yaml} and labels it {@code classpath:users.yaml}.
     * The method otherwise uses {@link #fallback}.
     * The classpath stream is closed after the parse.
     * The method ignores a close failure.
     *
     * @param properties The configuration that supplies the fallback username and password.
     * @return The loaded rows and their source label.
     * @throws IllegalStateException The chosen file or the fallback pair cannot be used.
     */
    private static Loaded loadCascade(CasseroleProperties properties) {
        Path cwd = Path.of("users.yaml");
        if (Files.isRegularFile(cwd)) {
            return loadFile(cwd);
        }
        String home = System.getProperty("user.home");
        Path userFile = Path.of(home, ".casserole", "users.yaml");
        if (Files.isRegularFile(userFile)) {
            return loadFile(userFile);
        }
        InputStream packed = UserAccounts.class.getClassLoader().getResourceAsStream("users.yaml");
        if (packed != null) {
            try {
                return parseStream(packed, "classpath:users.yaml");
            } finally {
                try {
                    packed.close();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        }
        return fallback(properties);
    }

    /**
     * The {@code loadFile} method reads one account file and parses it.
     * The source label is the absolute path.
     * The stream is closed after the parse.
     * The method ignores a close failure.
     *
     * @param path The account file to read.
     * @return The loaded rows and that absolute path.
     * @throws IllegalStateException The file cannot be read. The message starts with {@code cannot read users.yaml:}.
     */
    private static Loaded loadFile(Path path) {
        FileInputStream in = null;
        try {
            in = new FileInputStream(path.toFile());
            return parseStream(in, path.toAbsolutePath().toString());
        } catch (IOException ex) {
            throw new IllegalStateException("cannot read users.yaml: " + path + " — " + ex.getMessage(), ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                    // ignore
                }
            }
        }
    }

    /**
     * The {@code parseStream} method reads one YAML document into account rows.
     * YAML is the indentation text format of the file.
     * The root must be a map that holds a {@code users} list.
     * The method throws {@link IllegalStateException} when the document is empty, when the root is not a map, when {@code users} is missing, when {@code users} is not a list, when that list is empty, when a name is duplicated, or when no row is valid.
     * The thrown message includes the source label.
     * The method skips a non-map row.
     * The method skips a blank name.
     * The method skips a name that {@code RegionOwner.isValid} rejects.
     * The method skips an empty password.
     * The method prints each skip to standard error.
     * The {@link #stringOf} method trims a kept name and password.
     * The method stores the first copy of a name as a {@link UserEntry}.
     *
     * @param in The YAML bytes to read.
     * @param source The label stored with the rows and written into failure messages.
     * @return The valid rows and the same source label.
     * @throws IllegalStateException The document has no usable account list.
     */
    private static Loaded parseStream(InputStream in, String source) {
        Yaml yaml = new Yaml();
        Object root = yaml.load(in);
        if (root == null) {
            throw new IllegalStateException("users.yaml empty: " + source);
        }
        if (!(root instanceof Map)) {
            throw new IllegalStateException("users.yaml must be a map: " + source);
        }
        @SuppressWarnings("unchecked")
        Map<Object, Object> map = (Map<Object, Object>) root;
        Object usersObj = map.get("users");
        if (usersObj == null) {
            throw new IllegalStateException("users.yaml missing users: " + source);
        }
        if (!(usersObj instanceof List)) {
            throw new IllegalStateException("users.yaml users must be a list: " + source);
        }
        @SuppressWarnings("unchecked")
        List<Object> rows = (List<Object>) usersObj;
        if (rows.isEmpty()) {
            throw new IllegalStateException("users.yaml users list empty: " + source);
        }
        List<UserEntry> out = new ArrayList<UserEntry>();
        Set<String> seen = new HashSet<String>();
        for (int i = 0; i < rows.size(); i++) {
            Object rowObj = rows.get(i);
            if (!(rowObj instanceof Map)) {
                System.err.println("users.yaml skip non-map row in " + source);
                continue;
            }
            @SuppressWarnings("unchecked")
            Map<Object, Object> row = (Map<Object, Object>) rowObj;
            String name = stringOf(row.get("name"));
            String password = stringOf(row.get("password"));
            if (name.length() == 0) {
                System.err.println("users.yaml skip blank name in " + source);
                continue;
            }
            if (!RegionOwner.isValid(name)) {
                System.err.println("users.yaml skip invalid name '" + name + "' in " + source);
                continue;
            }
            if (password.length() == 0) {
                System.err.println("users.yaml skip empty password for " + name + " in " + source);
                continue;
            }
            if (seen.contains(name)) {
                throw new IllegalStateException("users.yaml duplicate name: " + name + " (" + source + ")");
            }
            seen.add(name);
            out.add(new UserEntry(name, password));
        }
        if (out.isEmpty()) {
            throw new IllegalStateException("users.yaml has no valid users: " + source);
        }
        return new Loaded(out, source);
    }

    /**
     * The {@code fallback} method builds one account from {@code casserole.ssh}.
     * The method uses an empty name and an empty password when the SSH section is null.
     * The method uses an empty password when the stored password is null.
     * The method throws {@link IllegalStateException} when {@code RegionOwner.isValid} rejects the name or the password is empty.
     * The message is {@code casserole.ssh fallback user/password is not usable}.
     * The source label is {@code casserole.ssh}.
     *
     * @param properties The configuration that supplies the username and password.
     * @return One {@link UserEntry} and the source label {@code casserole.ssh}.
     * @throws IllegalStateException The fallback name or password cannot be used.
     */
    private static Loaded fallback(CasseroleProperties properties) {
        CasseroleProperties.Ssh ssh = properties.getSsh();
        String name = ssh == null ? "" : ssh.getUsername();
        String password = ssh == null || ssh.getPassword() == null ? "" : ssh.getPassword();
        if (!RegionOwner.isValid(name) || password.length() == 0) {
            throw new IllegalStateException("casserole.ssh fallback user/password is not usable");
        }
        List<UserEntry> out = new ArrayList<UserEntry>();
        out.add(new UserEntry(name, password));
        return new Loaded(out, "casserole.ssh");
    }

    /**
     * The {@code stringOf} method turns one YAML value into trimmed text.
     * The method returns an empty string when the value is null.
     * The method returns {@code String.valueOf} trimmed for any other value.
     *
     * @param value The YAML scalar to read.
     * @return The trimmed text, or an empty string.
     */
    private static String stringOf(Object value) {
        if (value == null) {
            return "";
        }
        return String.valueOf(value).trim();
    }

    // @aa-sdd PK-0073 PK-0079 PK-0085 PK-0104 PK-0108 PK-0113 PK-0121 PK-0126 PK-0127 PK-0260 PK-0274 PK-0283 PK-3817 PK-4114 PK-4115 PK-4116 PK-4117 PK-4118 PK-4119 PK-4122 PK-4124 PK-4154 PK-4166 PK-4240 PK-4413 PK-4414 PK-4415 PK-4419 PK-4422 PK-4432 docs/specs/java/org/aetheris/swing/swing.spec.md
    // @aa-sdd PK-0461 docs/specs/java/org/casserole/ssh/ssh.spec.md
    // @aa-sdd PK-0462 PK-4437 docs/specs/java/org/casserole/app/ui/ssh/ssh.spec.md
    // @aa-sdd PK-0466 docs/specs/java/org/aetheris/wire/wire.spec.md
    // @aa-sdd PK-0487 PK-0510 PK-0530 PK-0533 PK-0534 PK-0535 PK-0537 PK-0538 PK-0539 PK-0540 PK-0541 PK-0542 PK-0544 PK-0546 PK-0547 PK-0548 PK-0550 PK-0551 PK-0552 PK-0553 PK-0554 PK-0555 PK-0556 PK-0557 docs/specs/java/org/casserole/casserole.spec.md
    // @aa-sdd PK-0558 PK-0559 PK-0560 docs/specs/java/org/casserole/format/format.spec.md
    // @aa-sdd PK-0562 PK-0730 PK-0732 docs/specs/java/org/casserole/llm/llm.spec.md
    // @aa-sdd PK-0734 PK-0735 PK-0758 PK-0759 PK-0780 PK-0793 PK-0794 PK-0814 PK-0815 PK-0816 PK-1569 PK-2040 PK-2277 PK-2325 PK-2467 PK-2751 PK-3175 PK-3317 PK-3459 PK-3460 PK-3464 PK-3465 PK-3466 PK-3467 PK-3468 PK-3479 PK-3480 PK-3481 PK-3482 PK-3483 PK-3489 PK-3490 docs/specs/java/org/roux/roux.spec.md
    // @aa-sdd PK-3514 PK-3522 PK-3546 PK-3547 PK-3548 PK-3552 PK-3576 PK-3582 PK-3617 PK-3636 PK-3687 PK-3689 PK-3690 PK-3692 PK-3695 PK-3696 PK-3697 PK-3698 PK-3710 PK-3717 PK-3729 PK-3730 PK-3739 PK-3754 PK-3787 PK-3790 PK-3792 PK-3793 PK-3794 PK-3796 PK-3799 PK-3800 PK-3802 PK-3803 PK-3804 PK-3806 PK-3807 PK-7815 PK-7864 docs/specs/java/org/aetheris/ssh/ssh.spec.md
    // @aa-sdd PK-4448 PK-4481 PK-4485 PK-4500 PK-4572 docs/specs/rust/aetheris/aetheris-client/src/chrome.spec.md
    /**
     * The {@code Loaded} type is one finished account load.
     * The type holds the rows and the source label.
     */
    private static final class Loaded {
        /**
         * The account rows from this load.
         */
        private final List<UserEntry> entries;

        /**
         * The label of the place those rows came from.
         */
        private final String source;

        /**
         * The constructor stores the rows and the source label.
         *
         * @param entries The account rows to keep.
         * @param source The origin label to keep.
         */
        private Loaded(List<UserEntry> entries, String source) {
            this.entries = entries;
            this.source = source;
        }
    }
}
/*****************************************************************************************
 * End of Class: UserAccounts.java 082626
 ****************************************************************************************/
